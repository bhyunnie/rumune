package com.trustprice.web.domain.jwt.api

import com.trustprice.web.domain.jwt.application.JwtService
import com.trustprice.web.domain.jwt.dto.response.RefreshAccessTokenResponse
import com.trustprice.web.global.enum.Responses
import com.trustprice.web.global.util.CookieUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtApi(
    private val jwtService: JwtService,
    private val cookieUtil: CookieUtil,
) {
    /**
     * access token 재발급
     */
    @GetMapping("/api/v1/jwt/refresh")
    fun refreshTokens(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): ResponseEntity<RefreshAccessTokenResponse> {
        val tokens = jwtService.regenerateTokensByRefreshToken(request.getHeader("Authorization"))
        val accessTokenCookie = cookieUtil.createAccessTokenCookie(tokens.accessToken)
        val refreshTokenCookie = cookieUtil.createRefreshTokenCookie(tokens.refreshToken)
        response.addCookie(accessTokenCookie)
        response.addCookie(refreshTokenCookie)
        return ResponseEntity.ok(
            RefreshAccessTokenResponse(
                message = "토큰 재발급 성공",
                status = Responses.OK,
                result = true,
            ),
        )
    }
}
