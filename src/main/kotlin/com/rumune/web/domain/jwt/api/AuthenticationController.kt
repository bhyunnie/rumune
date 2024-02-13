package com.rumune.web.domain.jwt.api

import com.rumune.web.domain.jwt.application.AuthenticationService
import com.rumune.web.domain.jwt.dto.AuthenticationRequestDto
import com.rumune.web.domain.jwt.dto.AuthenticationResponseDto
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/api/authenticate")
    fun authenticate(
        @RequestBody authenticationRequest: AuthenticationRequestDto,
        httpServletResponse: HttpServletResponse,
    ): AuthenticationResponseDto {
        val authenticationResponse = authenticationService.authentication(authenticationRequest)
        val cookie = createAuthCookie(authenticationResponse.accessToken)
        httpServletResponse.addCookie(cookie)
        return authenticationResponse
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/oauth2")
    fun oAuth2Authentication(@AuthenticationPrincipal user: User): Any {
        println(user);

        return "user"
    }

    private fun createAuthCookie(accessToken: String): Cookie {
        val cookie = Cookie("access-token", accessToken)
        cookie.path = "/"
        cookie.maxAge = 60 * 60 * 24 * 7
        return cookie
    }
}