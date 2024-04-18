package com.rumune.web.domain.jwt

import com.amazonaws.services.kms.model.NotFoundException
import com.rumune.web.domain.jwt.application.JwtService
import com.rumune.web.domain.user.entity.User
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
class JwtServiceTest @Autowired constructor(
    val jwtService: JwtService
) {
    lateinit var user:User

    @Test
    @DisplayName("Access Token 을 발급 받고 검증을 통과하여야 한다")
    fun test_1() {
        val token = jwtService.generateAccessToken(user.email)
        val emailFromToken = jwtService.getEmailOfToken(token)
        assert(jwtService.validToken(token, user, emailFromToken))
    }

    @Test
    @DisplayName("Refresh Token 을 발급 받고 검증을 통과해야한다. 저장이 되면 안된다.")
    fun test_2() {
        val refreshToken = jwtService.generateRefreshToken(user.email)
        val emailFromRefreshToken = jwtService.getEmailOfToken(refreshToken)
        assert(jwtService.validToken(refreshToken, user, emailFromRefreshToken))
        assertThrows<NotFoundException> {
            jwtService.findRefreshToken(refreshToken)
        }
    }

    @Test
    @DisplayName("Refresh Token 만 보냈을 경우 Access Token 을 재발급 해주어야 한다.")
    fun test_3() {
        val oldAccessToken = jwtService.generateAccessToken(user.email)
        val oldRefreshToken = jwtService.generateRefreshToken(user.email)
        val newTokenPair = jwtService.refreshTokens(oldRefreshToken)
        assert(oldAccessToken != newTokenPair["access_token"])
        assert(oldRefreshToken != newTokenPair["refresh_token"])
    }
}