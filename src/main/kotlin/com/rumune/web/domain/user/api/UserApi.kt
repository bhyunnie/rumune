package com.rumune.web.domain.user.api

import com.rumune.web.domain.user.dto.AuthenticationRequestDto
import com.rumune.web.domain.user.dto.AuthenticationResponseDto
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.dto.UserInfoRequestDto
import com.rumune.web.global.util.CookieUtil
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserApi(
    private val userService: UserService,
    private val cookieUtil: CookieUtil
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

    @PostMapping("/api/v1/signin")
    fun authenticate(
        @RequestBody authenticationRequest: AuthenticationRequestDto,
        httpServletResponse: HttpServletResponse,
    ): AuthenticationResponseDto {
        val authenticationResponse = userService.authentication(authenticationRequest)
        val cookie = cookieUtil.createAccessTokenCookie(authenticationResponse.accessToken)
        httpServletResponse.addCookie(cookie)
        return authenticationResponse
    }
}