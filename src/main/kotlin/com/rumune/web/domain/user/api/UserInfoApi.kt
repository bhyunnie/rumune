package com.rumune.web.domain.user.api

import com.rumune.web.domain.user.application.UserInfoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserInfoApi(userInfoService: UserInfoService) {
    @GetMapping("/api/v1/user/list")
    fun findUserList (): ResponseEntity<String> {
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
}