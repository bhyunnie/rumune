package com.trustprice.web.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val issuer: String,
    val secretKey: String,
    val accessTokenExpireDuration: Long,
    val refreshTokenExpireDuration: Long,
)
