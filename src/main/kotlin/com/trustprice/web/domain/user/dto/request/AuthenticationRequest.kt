package com.trustprice.web.domain.user.dto.request

data class AuthenticationRequest(
    val email: String,
    val password: String,
)
