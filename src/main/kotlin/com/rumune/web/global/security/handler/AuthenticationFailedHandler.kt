package com.rumune.web.global.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class AuthenticationFailedHandler : AuthenticationEntryPoint {
    @Override
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?,
    ) {
        response?.contentType = "application/json"
        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.characterEncoding = "UTF-8"
        response?.writer?.write("{\"status\": \"INTERNAL_SERVER_ERROR\",\"code\": \"NP\", \"message\": \"권한이 없습니다.\"}")
    }
}
