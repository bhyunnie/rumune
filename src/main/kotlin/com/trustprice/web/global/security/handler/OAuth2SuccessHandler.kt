package com.trustprice.web.global.security.handler

import com.amazonaws.services.kms.model.NotFoundException
import com.trustprice.web.domain.jwt.application.JwtService
import com.trustprice.web.domain.user.application.UserService
import com.trustprice.web.domain.user.entity.User
import com.trustprice.web.global.util.CookieUtil
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val cookieUtil: CookieUtil,
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?,
    ) {
        if (authentication == null) throw NotFoundException("인증 정보가 없습니다.")
        val user: User =
            run {
                val principal = authentication.principal as DefaultOAuth2User
                val providerId = principal.attributes["id"].toString()
                userService.findUserByProviderId(providerId)
            }
        val accessTokenCookie: Cookie =
            run {
                val accessToken = jwtService.generateAccessToken(user.email)
                cookieUtil.createAccessTokenCookie(accessToken)
            }
        val refreshTokenCookie =
            run {
                val refreshToken = jwtService.generateRefreshToken(user.email)
                jwtService.updateJwt(user.id, refreshToken)
                cookieUtil.createRefreshTokenCookie(refreshToken)
            }
        response.apply {
            addCookie(accessTokenCookie)
            addCookie(refreshTokenCookie)
            sendRedirect("http://localhost:3000")
        }
    }
}
