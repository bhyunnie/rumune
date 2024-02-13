package com.rumune.web.domain.jwt.application

import com.rumune.web.domain.jwt.dto.AuthenticationRequestDto
import com.rumune.web.domain.jwt.dto.AuthenticationResponseDto
import com.rumune.web.domain.jwt.entity.RefreshToken
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.entity.User
import com.rumune.web.domain.user.repository.RefreshTokenRepository
import com.rumune.web.global.properties.JwtProperties
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService,
    private val jwtService: JwtService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun authentication(authenticationRequest: AuthenticationRequestDto): AuthenticationResponseDto {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(authenticationRequest.email, authenticationRequest.password))
        val user = userService.loadUserByUsername(authenticationRequest.email)
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)
        val refreshTokenOptional = refreshTokenRepository.findByEmail(user.email)
        if (refreshTokenOptional.isEmpty) {
            refreshTokenRepository.save(RefreshToken(email=user.email, refreshToken=refreshToken, userId = user.userId))
        } else {
            val savedRefreshToken = refreshTokenOptional.get()
            savedRefreshToken.refreshToken = refreshToken
            refreshTokenRepository.save(savedRefreshToken)
        }

        return AuthenticationResponseDto(
            userId = user.id,
            email = user.email,
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    private fun createAccessToken(user:User):String {
        return jwtService.generateToken(
            email = user.email,
            expiredAt = getAccessTokenExpireDuration()
        )
    }

    private fun createRefreshToken(user:User):String {
        return jwtService.generateToken(
            email = user.email,
            expiredAt = getRefreshTokenExpireDuration()
        )
    }

    private fun getAccessTokenExpireDuration(): Date {
        return Date(System.currentTimeMillis() + jwtProperties.accessTokenExpireDuration)
    }

    private fun getRefreshTokenExpireDuration(): Date {
        return Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpireDuration)
    }
}