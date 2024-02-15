package com.rumune.web.domain.user.application

import com.rumune.web.domain.user.entity.*
import com.rumune.web.domain.user.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component
import java.util.Collections

@Component
class CustomOAuth2UserService(
    private val userService: UserService,
    private val userRepository: UserRepository
): DefaultOAuth2UserService() {
    @Override
    override fun loadUser(request: OAuth2UserRequest):OAuth2User {
        try {
            val userInfo: OAuth2UserInfo?
            val oAuth2User:OAuth2User = super.loadUser(request)
            val provider = request.clientRegistration.registrationId

            userInfo = when(provider.uppercase()) {
                "GOOGLE" -> GoogleUserInfo(oAuth2User.attributes)
                "KAKAO" -> KakaoUserInfo(oAuth2User.attributes)
                "DISCORD" -> DiscordUserInfo(oAuth2User.attributes)
                else -> null
            }

            if(userInfo == null) return oAuth2User

            val isNew = userRepository.findByEmail(userInfo.getEmail()).isEmpty
            if (isNew) {
                userRepository.save(
                    User(
                        provider = userInfo.getProvider(),
                        email = userInfo.getEmail(),
                        pwd = "",
                        profileImage = userInfo.getProfileImage(),
                        providerId = userInfo.getId(),
                        name = userInfo.getName()
                    )
                )
                userService.addAuthority(userInfo.getEmail(),"ROLE_USER")
            }
            return DefaultOAuth2User(Collections.singleton(SimpleGrantedAuthority("ROLE_USER")), userInfo.getAttributes(), "email")
        } catch (e:Exception) {
            throw e
        }
    }
}