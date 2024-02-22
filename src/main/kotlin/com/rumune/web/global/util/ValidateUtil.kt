package com.rumune.web.global.util

import com.rumune.web.domain.common.enum.Responses
import com.rumune.web.domain.user.dto.UserInfoResponseDto
import com.rumune.web.global.security.filter.JwtAuthenticationFilter
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ValidateUtil(
    private val jwtUtil: JwtUtil
) {
    fun extractEmailFromBearerToken(request:HttpServletRequest):String {
        val authorizationHeader: String = request.getHeader(JwtAuthenticationFilter.HEADER_AUTHORIZATION)
            ?: throw Exception("인증 헤더가 누락되었습니다.")
        val token = authorizationHeader.substringAfter("Bearer ")
        if (jwtUtil.isExpired(token)) throw Exception("만료된 토큰입니다.")
        val email = jwtUtil.getEmailOfToken(token) ?: throw Exception("잘못된 인증 토큰입니다.")
        return email
    }
}