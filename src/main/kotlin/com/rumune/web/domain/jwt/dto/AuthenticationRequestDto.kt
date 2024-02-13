package com.rumune.web.domain.jwt.dto

data class AuthenticationRequestDto (
    val email:String,
    val password:String,
)