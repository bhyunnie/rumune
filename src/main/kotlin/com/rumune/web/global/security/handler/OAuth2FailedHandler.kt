package com.rumune.web.global.security.handler

import com.rumune.web.global.exception.OAuth2AlreadyExistException
import com.rumune.web.global.properties.ClientProperties
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class OAuth2FailedHandler(
    private val clientProperties: ClientProperties
):AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        if (exception is OAuth2AlreadyExistException) {
            response?.sendRedirect("${clientProperties.url}/login/oauth2/failure?email=${exception.email}&provider=${exception.provider}")
        } else {
            response?.contentType = "application/json"
            response?.status = HttpServletResponse.SC_FORBIDDEN
            response?.characterEncoding = "UTF-8"
            response?.writer?.write("{\"status\": \"INTERNAL_SERVER_ERROR\",\"code\": \"NP\", \"message\": \"${exception?.message}\"}");
        }
    }
}