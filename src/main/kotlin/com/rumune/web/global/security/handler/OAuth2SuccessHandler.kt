package com.rumune.web.global.security.handler

import com.amazonaws.services.kms.model.NotFoundException
import com.rumune.web.domain.jwt.application.JwtService
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.global.util.CookieUtil
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
        val principal = authentication.principal as DefaultOAuth2User
        val providerId = principal.attributes["id"].toString()
        val user = userService.findUserByProviderId(providerId)
        val accessToken = jwtService.generateAccessToken(user.email)
        val refreshToken = jwtService.generateRefreshToken(user.email)
        val accessTokenCookie = cookieUtil.createAccessTokenCookie(accessToken)
        val refreshTokenCookie = cookieUtil.createRefreshTokenCookie(refreshToken)
        jwtService.updateJwt(user.id, refreshToken)
        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)
        response.sendRedirect("http://localhost:3000")
    }
}
