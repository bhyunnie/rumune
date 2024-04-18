package com.rumune.web.global.security.Interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.rumune.web.domain.jwt.application.JwtService
import com.rumune.web.global.util.JwtUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor(
    private val jwtService: JwtService,
    private val jwtUtil: JwtUtil,
    private val objectMapper: ObjectMapper
): HandlerInterceptor {
    @Override
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        try {
            val id = jwtUtil.extractUserIdFromBearerToken(request)
            request.setAttribute("userId", id)
            return true
        } catch (e:Exception) {
            return false
        }
    }
}