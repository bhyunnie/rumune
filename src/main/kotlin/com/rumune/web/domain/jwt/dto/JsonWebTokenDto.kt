package com.rumune.web.domain.jwt.dto

import com.rumune.web.domain.jwt.entity.JsonWebToken

class JsonWebTokenDto(
    val userId: Long,
    val token: String,
) {
    companion object {
        fun from(j: JsonWebToken): JsonWebTokenDto {
            return JsonWebTokenDto(
                userId = j.userId,
                token = j.jwt,
            )
        }
    }
}
