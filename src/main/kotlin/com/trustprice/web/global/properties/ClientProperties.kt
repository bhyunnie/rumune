package com.trustprice.web.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "client")
data class ClientProperties(
    val url: String,
)
