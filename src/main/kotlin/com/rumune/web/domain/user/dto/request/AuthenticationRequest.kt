package com.rumune.web.domain.user.dto.request

data class AuthenticationRequest(
    val email: String,
    val password: String,
)
