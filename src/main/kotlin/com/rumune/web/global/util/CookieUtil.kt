package com.rumune.web.global.util

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component

@Component
class CookieUtil() {
    fun createAccessTokenCookie(token:String): Cookie {
        val cookie = Cookie("access-token", token)
        cookie.path = "/"
        cookie.maxAge = 60 * 60 * 24
        return cookie
    }
}