package com.rumune.web.global.security.filter

import com.rumune.web.domain.jwt.application.JwtService
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.global.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.tomcat.websocket.AuthenticationException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
) : OncePerRequestFilter() {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
    }

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val authorizationHeader: String? = request.getHeader(HEADER_AUTHORIZATION)
            if (authorizationHeader.hasNotToken()) {
                filterChain.doFilter(request, response)
                return
            }

            if (authorizationHeader == null) throw AuthenticationException("인증 헤더 값을 찾을 수 없습니다.")

            val token = authorizationHeader.extractTokenValue()
            val email = jwtService.getEmailOfToken(token)

            if (email.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val userInfo = userService.findUserByEmail(email)
                val foundUser = userService.loadUserByUsername(userInfo.email)
                if (jwtService.validToken(token, foundUser, email)) updateContext(foundUser, request)
            }
            val userId = jwtUtil.extractUserIdFromBearerToken(request)
            request.setAttribute("userId", userId)
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            throw e
        }
    }

    private fun updateContext(
        user: UserDetails,
        request: HttpServletRequest,
    ) {
        val authenticationToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    private fun String?.hasNotToken(): Boolean {
        return this == null || !this.startsWith("Bearer ")
    }

    private fun String.extractTokenValue(): String {
        return this.substringAfter("Bearer ")
    }
}
