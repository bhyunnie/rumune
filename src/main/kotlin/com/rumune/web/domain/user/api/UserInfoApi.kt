package com.rumune.web.domain.user.api

import com.rumune.web.domain.user.application.UserInfoService
import com.rumune.web.domain.user.dto.UserInfoRequestDto
import com.rumune.web.domain.user.dto.UserSignUpRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserInfoApi(
    private val userInfoService: UserInfoService
) {
    @GetMapping("/api/v1/user/list")
    fun findUserList (@ModelAttribute userInfoRequestDto: UserInfoRequestDto): ResponseEntity<String> {
        return ResponseEntity<String>(
            "this is user list",null,HttpStatus.OK
        )
    }

    @GetMapping("/api/v1/user/{userId}")
    fun findUserByUserId (@PathVariable userId: String): ResponseEntity<String> {
        return ResponseEntity<String>(
            "this is find by $userId",null,HttpStatus.OK
        )
    }

    @PostMapping("/api/v1/user/signup")
    fun signup(@RequestBody userSignUpRequestDto: UserSignUpRequestDto): ResponseEntity<String> {
        userInfoService.createUser(userSignUpRequestDto.email, userSignUpRequestDto.nickname , userSignUpRequestDto.provider)
        return ResponseEntity<String>(
            "good", null, HttpStatus.OK
        )
    }
}