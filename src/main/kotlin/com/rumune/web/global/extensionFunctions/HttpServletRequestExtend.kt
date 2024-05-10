package com.rumune.web.global.extensionFunctions

import jakarta.servlet.http.HttpServletRequest

fun HttpServletRequest.getUserId(): Long {
    return this.getAttribute("userId") as Long
}
