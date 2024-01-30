package com.rumune.web.domain.user.dto

data class UserSignUpRequestDto(
    val email: String,
    val nickname: String,
    val provider: String
) {
}