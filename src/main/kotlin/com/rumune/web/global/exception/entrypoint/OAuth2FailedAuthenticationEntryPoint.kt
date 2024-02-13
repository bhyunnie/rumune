package com.rumune.web.global.exception.entrypoint

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class OAuth2FailedAuthenticationEntryPoint: AuthenticationEntryPoint {
    @Override
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.contentType = "application/json"
        response?.status = HttpServletResponse.SC_FORBIDDEN
        response?.characterEncoding = "UTF-8"
        response?.writer?.write("{\"code\": \"NP\", \"message\": \"권한이 없습니다.\"}");
    }
}