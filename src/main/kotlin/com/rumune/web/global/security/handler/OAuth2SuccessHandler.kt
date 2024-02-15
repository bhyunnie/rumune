package com.rumune.web.global.security.handler

import com.rumune.web.domain.jwt.application.RefreshTokenService
import com.rumune.web.domain.jwt.entity.RefreshToken
import com.rumune.web.global.util.JwtUtil
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.entity.User
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
    private val refreshTokenService: RefreshTokenService,
    private val jwtUtil: JwtUtil,
    private val cookieUtil:CookieUtil
): AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        if (authentication == null) throw Exception("Authentication is null.")
        val principal = authentication.principal as DefaultOAuth2User
        val providerId = principal.attributes["id"].toString()
        val user = userService.findUserByProviderId(providerId).get() // TODO 이부분 나중에 성능 개선에 들어가야할듯
        val accessToken = jwtUtil.generateAccessToken(user.email)
        val refreshToken = jwtUtil.generateRefreshToken(user.email)
        val cookie = cookieUtil.createAccessTokenCookie(accessToken)

        saveRefreshToken(user, refreshToken)

        response.addCookie(cookie)
        request.getRequestDispatcher("/api/oauth2").forward(request,response)
    }

    private fun saveRefreshToken(user: User, newRefreshToken: String) {
        val refreshTokenOptional = refreshTokenService.findRefreshToken(user.userId)
        val refreshToken:RefreshToken
        if(refreshTokenOptional.isPresent) {
            refreshToken = refreshTokenOptional.get()
            refreshToken.refreshToken = newRefreshToken
        } else {
            refreshToken = RefreshToken(userId = user.userId, refreshToken = newRefreshToken)
        }
        refreshTokenService.save(refreshToken)
    }
}