package com.rumune.web.domain.user.dto.response

class AuthenticationResponse (
    val userId: Long,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)