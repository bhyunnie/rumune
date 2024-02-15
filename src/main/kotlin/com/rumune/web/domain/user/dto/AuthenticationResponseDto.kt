package com.rumune.web.domain.user.dto

class AuthenticationResponseDto (
    val userId: Long,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)