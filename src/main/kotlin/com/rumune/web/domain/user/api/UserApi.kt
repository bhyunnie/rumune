package com.rumune.web.domain.user.api

import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.dto.SignInUserRequestDto
import com.rumune.web.domain.user.dto.UserInfoRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserApi(
    private val userService: UserService
) {
    @GetMapping("/api/v1/user/list")
    fun findUserList(@ModelAttribute userInfoRequestDto: UserInfoRequestDto): ResponseEntity<String> {
        return ResponseEntity<String>(
            "this is user list", null, HttpStatus.OK
        )
    }

    @GetMapping("/api/v1/user/{userId}")
    fun findUserByUserId(@PathVariable userId: String): ResponseEntity<String> {
        return ResponseEntity<String>(
            "this is find by $userId", null, HttpStatus.OK
        )
    }

    @PostMapping("/signin")
    fun login(request:SignInUserRequestDto):ResponseEntity<String> {
        val user = userService.findUserByEmail(request.email)
        return ResponseEntity.ok(
            "success"
        )
    }
}