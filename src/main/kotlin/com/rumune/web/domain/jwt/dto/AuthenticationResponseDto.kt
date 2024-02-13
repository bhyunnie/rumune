package com.rumune.web.domain.jwt.dto

class AuthenticationResponseDto (
    val userId: Long,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)