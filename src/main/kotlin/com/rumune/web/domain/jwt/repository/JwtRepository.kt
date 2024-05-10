package com.rumune.web.domain.jwt.repository

import com.rumune.web.domain.jwt.entity.JsonWebToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface JwtRepository : JpaRepository<JsonWebToken, Long> {
    fun findByUserId(userId: Long): Optional<JsonWebToken>

    fun findByJwt(refreshToken: String): Optional<JsonWebToken>
}
