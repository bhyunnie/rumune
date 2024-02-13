package com.rumune.web.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("jwt")
data class JwtProperties (
    val issuer: String,
    val secretKey: String,
    val accessTokenExpireDuration: Long,
    val refreshTokenExpireDuration: Long,
) {
}