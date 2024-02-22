package com.rumune.web.domain.user.dto

import com.rumune.web.domain.user.entity.Authority
import com.rumune.web.domain.user.entity.User

data class UserDto(
    var userId: Long,
    var email: String,
    var createdAt: String,
    var provider: String,
    var providerId: String,
    var username: String,
    var profileImage:String,
    var deprecated: Boolean,
    var authorities: Set<Authority>
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                userId = user.userId,
                email = user.email,
                createdAt = user.createdAt.toString(),
                provider = user.provider,
                providerId = user.providerId.toString(),
                username = user.username,
                profileImage = user.profileImage.toString(),
                deprecated = user.deprecated,
                authorities = user.authorities
            )
        }
    }
}