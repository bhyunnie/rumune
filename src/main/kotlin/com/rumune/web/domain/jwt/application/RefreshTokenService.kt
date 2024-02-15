package com.rumune.web.domain.jwt.application

import com.rumune.web.domain.jwt.entity.RefreshToken
import com.rumune.web.domain.jwt.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun save(refreshToken: RefreshToken): RefreshToken {
        return refreshTokenRepository.save(refreshToken)
    }
    fun findRefreshToken(userId:Long):Optional<RefreshToken> {
        return refreshTokenRepository.findByUserId(userId)
    }
}