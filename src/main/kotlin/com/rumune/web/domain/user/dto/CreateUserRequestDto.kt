package com.rumune.web.domain.user.dto

import com.rumune.web.domain.user.entity.User

class CreateUserRequestDto(
    val email: String,
    val provider: String,
    val providerId: String,
    val username: String,
    val profileImage: String?,
    val pwd: String,
) {
    companion object {
        fun from (user:User) {
            CreateUserRequestDto(
                email = user.email,
                provider = user.provider,
                providerId = user.providerId.toString(),
                username = user.username,
                profileImage = user.profileImage,
                pwd = user.pwd,
            )
        }
    }
}