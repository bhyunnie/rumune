package com.rumune.web.domain.user.dto

data class UserSignUpRequestDto(
    val email: String,
    val name: String,
    val provider: String,
    val profileImage:String,
) {
}