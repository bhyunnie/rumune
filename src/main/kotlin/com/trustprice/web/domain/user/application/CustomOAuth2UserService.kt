package com.trustprice.web.domain.user.application

import com.trustprice.web.domain.cart.entity.Cart
import com.trustprice.web.domain.cart.repository.CartRepository
import com.trustprice.web.domain.user.entity.*
import com.trustprice.web.domain.user.enum.Providers
import com.trustprice.web.domain.user.repository.UserRepository
import com.trustprice.web.global.exception.OAuth2AlreadyExistException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class CustomOAuth2UserService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository,
) : DefaultOAuth2UserService() {
    /**
     * 유저 정보 가져옴
     * CASE
     * 유저가 존재함 : 유저의 권한 확인
     * 유저가 존재하지 않음 : 신규 가입
     */
    @Override
    override fun loadUser(request: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(request)
        val provider = request.clientRegistration.registrationId
        val userInfo: OAuth2UserInfo = extractUserInfo(provider, oAuth2User)
        val authorities: List<SimpleGrantedAuthority> = extractUserAuthority(userInfo)
        return DefaultOAuth2User(authorities, userInfo.claims, "email")
    }

    /**
     * 각 provider 에 맞춘 userInfo 주입
     */
    private fun extractUserInfo(
        provider: String,
        oAuth2User: OAuth2User,
    ): OAuth2UserInfo {
        return when (provider.uppercase()) {
            Providers.GOOGLE.name -> GoogleUserInfo(oAuth2User)
            Providers.KAKAO.name -> KakaoUserInfo(oAuth2User)
            Providers.DISCORD.name -> DiscordUserInfo(oAuth2User)
            Providers.NAVER.name -> NaverUserInfo(oAuth2User)
            else -> throw Exception("지원하지 않는 서비스입니다.")
        }
    }

    /**
     * email 을 통해 유저를 조회 후 유저로부터 권한을 추출
     */
    private fun extractUserAuthority(userInfo: OAuth2UserInfo): List<SimpleGrantedAuthority> {
        val userOptional = userRepository.findByEmail(userInfo.email)
        if (userOptional.isEmpty) {
            val user = saveUserByUserInfo(userInfo)
            saveCart(user)
            return listOf(SimpleGrantedAuthority(addAuthority(user, "ROLE_USER").authority))
        } else if (userOptional.get().provider != userInfo.provider) {
            throw OAuth2AlreadyExistException("이미 다른 서비스를 통해 가입 내역이 있는 이메일입니다.", userOptional.get().provider, userOptional.get().email)
        } else {
            return userOptional.get().authorities.map { SimpleGrantedAuthority(it.name) }
        }
    }

    /**
     * userInfo 를 통한 유저 저장
     */
    private fun saveUserByUserInfo(userInfo: OAuth2UserInfo): User {
        return userRepository.save(
            User(
                provider = userInfo.provider,
                email = userInfo.email,
                pwd = "",
                profileImage = userInfo.profileImage,
                providerId = userInfo.id,
                name = userInfo.name,
            ),
        )
    }

    /**
     * 카트 저장
     */
    private fun saveCart(user: User): Cart {
        return cartRepository.save(Cart(user = user))
    }

    /**
     * 권한 추가
     */
    private fun addAuthority(
        user: User,
        authority: String,
    ): Authority {
        return userService.addAuthority(user.email.toString(), authority)
    }
}
