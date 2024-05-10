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
class JwtServiceTest
    @Autowired
    constructor(
        val jwtService: JwtService,
    ) {
        val user =
            User(
                id = 0,
                name = "안병현",
                pwd = "",
                deprecated = false,
                email = "test@test.rumune",
                provider = "naver",
                providerId = "55115515151",
                profileImage = "https://phinf.pstatic.net/contact/20220621_259/1655811890175gSrYf_JPEG/image.jpg",
            )

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
    }
