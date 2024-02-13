package com.rumune.web.domain.common.application

import com.rumune.web.domain.jwt.entity.RefreshToken
import com.rumune.web.domain.user.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun findByRefreshToken(refreshToken: String): Optional<RefreshToken> {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
    }

    fun findByUserId(userId:Long): Optional<RefreshToken> {
        return refreshTokenRepository.findByUserId(userId)
    }
}