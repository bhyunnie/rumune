package com.rumune.web.global.security.handler

import com.rumune.web.domain.user.application.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val userService: UserService
): AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        if (authentication == null) throw Exception("Authentication is null.")
        val principal = authentication.principal as DefaultOAuth2User
        val providerId = principal.attributes["id"].toString()
        val user = userService.findUserByProviderId(providerId)
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user,null,user.getAuthorities())

        request.getRequestDispatcher("/").forward(request,response)
    }
}