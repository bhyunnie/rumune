package com.rumune.web.global.security.handler

import com.rumune.web.domain.jwt.entity.JsonWebToken
import com.rumune.web.domain.jwt.application.JwtService
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
    private val jwtService: JwtService,
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
        val user = userService.findUserByProviderId(providerId).firstOrNull() ?: throw Exception("유저가 없습니다.")
        val accessToken = jwtService.generateAccessToken(user.email)
        val refreshToken = jwtService.generateRefreshToken(user.email)
        val accessTokenCookie = cookieUtil.createAccessTokenCookie(accessToken)
        val refreshTokenCookie = cookieUtil.createRefreshTokenCookie(refreshToken)

        saveRefreshToken(user, refreshToken)

        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)
        response.sendRedirect("http://localhost:3000")
    }

    private fun saveRefreshToken(user: User, newRefreshToken: String) {
        val refreshToken = jwtService.findJwt(user.userId)
        val jsonWebToken:JsonWebToken
        if(refreshToken.isNotEmpty()) {
            jsonWebToken = refreshToken[0]
            jsonWebToken.jwt = newRefreshToken
        } else {
            jsonWebToken = JsonWebToken(userId = user.userId, jwt = newRefreshToken)
        }
        jwtService.save(jsonWebToken)
    }
}