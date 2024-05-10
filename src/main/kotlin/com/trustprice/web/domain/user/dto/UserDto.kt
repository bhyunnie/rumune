package com.trustprice.web.domain.user.dto

import com.trustprice.web.domain.user.entity.Authority
import com.trustprice.web.domain.user.entity.User

data class UserDto(
    var id: Long,
    var email: String,
    var createdAt: String,
    var provider: String,
    var providerId: String,
    var username: String,
    var profileImage: String,
    var deprecated: Boolean,
    var authorities: Set<Authority>,
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                id = user.id,
                email = user.email,
                createdAt = user.createdAt.toString(),
                provider = user.provider,
                providerId = user.providerId.toString(),
                username = user.username,
                profileImage = user.profileImage.toString(),
                deprecated = user.deprecated,
                authorities = user.authorities,
            )
        }
    }
}
