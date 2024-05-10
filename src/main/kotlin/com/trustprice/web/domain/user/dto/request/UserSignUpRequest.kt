package com.trustprice.web.domain.user.dto.request

data class UserSignUpRequest(
    val email: String,
    val name: String,
    val provider: String,
    val profileImage: String,
)
