package com.rumune.web.global.security.filter

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
    private val jwtUtil: JwtUtil,
    private val userService: UserService,
): OncePerRequestFilter() {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
    }

    @Override
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader:String? = request.getHeader(HEADER_AUTHORIZATION)
       if (authorizationHeader.hasNotToken()) {
           filterChain.doFilter(request,response)
           return
       }

        if (authorizationHeader == null) throw AuthenticationException("인증 헤더 값을 찾을 수 없습니다.")

        val token = authorizationHeader.extractTokenValue()
        val email = jwtUtil.getEmailOfToken(token)

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val userInfo = userService.findUserByEmail(email).get()
            val foundUser = userService.loadUserByUsername(userInfo.email)
            if(jwtUtil.validToken(token, foundUser)) {
                updateContext(foundUser, request)
            }
        }
        filterChain.doFilter(request,response)
    }

    private fun updateContext(user:UserDetails,request:HttpServletRequest) {
        val authenticationToken = UsernamePasswordAuthenticationToken(user,null,user.authorities)
        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
    }

    private fun String?.hasNotToken():Boolean {
        return this == null || !this.startsWith("Bearer ")
    }

    private fun String.extractTokenValue():String {
        return this.substringAfter("Bearer ")
    }
}