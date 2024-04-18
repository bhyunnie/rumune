package com.rumune.web.domain.jwt.api

import com.rumune.web.domain.jwt.application.JwtService
import com.rumune.web.domain.jwt.dto.response.RefreshAccessTokenResponse
import com.rumune.web.global.enum.Responses
import com.rumune.web.global.util.CookieUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JwtApi(
    private val jwtService: JwtService,
    private val cookieUtil: CookieUtil
) {
    /**
     * access token 발급
     */
    @GetMapping("/api/v1/jwt/refresh")
    fun getAccessToken(request:HttpServletRequest, response: HttpServletResponse):ResponseEntity<RefreshAccessTokenResponse> {
        val tokenPair = jwtService.refreshTokens(request.getHeader("Authorization"))
        if(tokenPair.isEmpty()) {
            return ResponseEntity.ok(
            RefreshAccessTokenResponse(
                message = "토큰 재발급 실패",
                status = Responses.ERROR,
                result = false
            ))
        } else {
            val accessTokenCookie = cookieUtil.createAccessTokenCookie(tokenPair["accessToken"].toString())
            val refreshTokenCookie = cookieUtil.createRefreshTokenCookie(tokenPair["refreshToken"].toString())
            response.addCookie(accessTokenCookie)
            response.addCookie(refreshTokenCookie)
            return ResponseEntity.ok(
            RefreshAccessTokenResponse(
                message = "토큰 재발급 성공",
                status = Responses.OK,
                result = true
            ))
        }
    }
}