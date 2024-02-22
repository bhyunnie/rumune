package com.rumune.web.global.util

import com.rumune.web.global.properties.JwtProperties
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class CookieUtil(
    private val jwtProperties: JwtProperties
) {
    fun createAccessTokenCookie(token:String): Cookie {
        val cookie = Cookie("access-token", token)
        cookie.path = "/"
        cookie.maxAge = (jwtProperties.accessTokenExpireDuration / 1000.0).toInt()
        return cookie
    }
}