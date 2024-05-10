package com.rumune.web.global.util

import com.rumune.web.domain.jwt.application.JwtService
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.global.security.filter.JwtAuthenticationFilter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class JwtUtil(
    private val jwtService: JwtService,
    private val userService: UserService,
) {
    fun extractEmailFromBearerToken(request: HttpServletRequest): String {
        val authorizationHeader: String =
            request.getHeader(JwtAuthenticationFilter.HEADER_AUTHORIZATION)
                ?: throw Exception("인증 헤더가 누락되었습니다.")
        val token = authorizationHeader.substringAfter("Bearer ")
        if (jwtService.isExpired(token)) throw Exception("만료된 토큰입니다.")
        val email = jwtService.getEmailOfToken(token) ?: throw Exception("잘못된 인증 토큰입니다.")
        return email
    }

    fun extractUserIdFromBearerToken(request: HttpServletRequest): Long {
        val email = extractEmailFromBearerToken(request)
        val user = userService.findUserByEmail(email)
        return user.id
    }
}
