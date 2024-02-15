package com.rumune.web.domain.jwt.repository

import com.rumune.web.domain.jwt.entity.RefreshToken
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByUserId(userId:Long): Optional<RefreshToken>
    fun findByRefreshToken(refreshToken: String): Optional<RefreshToken>
}