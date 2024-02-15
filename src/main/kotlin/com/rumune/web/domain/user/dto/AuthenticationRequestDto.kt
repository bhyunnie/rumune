package com.rumune.web.domain.user.dto

data class AuthenticationRequestDto (
    val email:String,
    val password:String,
)