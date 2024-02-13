package com.rumune.web

import com.rumune.web.global.exception.response.UserLoginForm
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.dto.CreateUserRequestDto
import com.rumune.web.domain.user.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtRequestTest {

//    @Autowired
//    private val userService:UserService = UserService()


    @BeforeEach
    fun before(userRepository: UserRepository,
    userService: UserService) {
        userRepository.deleteAll()
        userService.createUser(CreateUserRequestDto(
            email = "test@test.com",
            username = "user1",
            provider = "rumune",
            profileImage = "test Image",
            providerId = "1281725012739",
            pwd = "1111"
        ))
    }

    @DisplayName("1. hello 메세지를 받아온다...")
    @Test
    fun test_1() {
        val client = RestTemplate()
        val entity: HttpEntity<UserLoginForm> = HttpEntity(UserLoginForm(email = "qudgus9601", password= "1111"))
        val result = client.exchange<String>("/login", HttpMethod.POST,entity, UserLoginForm::class.java)
        println(result.headers[HttpHeaders.AUTHORIZATION]?.get(0));
        println(result.body)
    }
}