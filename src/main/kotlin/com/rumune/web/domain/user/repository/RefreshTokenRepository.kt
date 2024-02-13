package com.rumune.web.domain.user.repository

import com.rumune.web.domain.jwt.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByUserId(userId:Long): Optional<RefreshToken>
    fun findByRefreshToken(refreshToken: String): Optional<RefreshToken>

    fun findByEmail(email:String): Optional<RefreshToken>
}