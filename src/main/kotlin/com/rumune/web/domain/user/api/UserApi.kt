package com.rumune.web.domain.user.api

import com.rumune.web.domain.common.enum.Responses
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.dto.*
import com.rumune.web.global.util.CookieUtil
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserApi(
    private val userService: UserService,
    private val cookieUtil: CookieUtil,
    private val validateUtil: ValidateUtil
) {
    // GET
    @GetMapping("/api/v1/user/list")
    fun findUserList(): ResponseEntity<UserInfoResponseDto> {
        val userList = userService.findAll()
        return ResponseEntity.ok(
            UserInfoResponseDto(
                Responses.OK,
                "유저 정보 조회 완료",
                userList
            )
        )
    }

    @GetMapping("/api/v1/user/{userId}")
    fun findUserByUserId(@PathVariable userId: String): ResponseEntity<String> {
        return ResponseEntity<String>(
            "this is find by $userId", null, HttpStatus.OK
        )
    }

    @GetMapping("/api/v1/user/me")
    fun findUserProfile(request:HttpServletRequest):ResponseEntity<UserInfoResponseDto> {
        try {
            val email = validateUtil.extractEmailFromBearerToken(request)
            val userList = userService.findUserByEmail(email)
            return ResponseEntity.ok(UserInfoResponseDto(Responses.OK, "API 요청 완료", userList))
        } catch (e:Exception) {
            return ResponseEntity.ok(UserInfoResponseDto(Responses.ERROR, e.message.toString(), listOf()))
        }
    }

    @GetMapping("/api/v1/admin/check/authority")
    fun checkAdminAuthority(request:HttpServletRequest):ResponseEntity<CheckUserAuthority> {
        try {
            val email = validateUtil.extractEmailFromBearerToken(request)
            val result = userService.checkAuthority(email, "ROLE_ADMIN")
            return ResponseEntity.ok(CheckUserAuthority(
                Responses.OK,
                "확인 완료",
                result))
        } catch (e:Exception) {
            return ResponseEntity.ok(CheckUserAuthority(
                Responses.ERROR,
                "권한 없음",
                false))
        }
    }

    // POST
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

    @PostMapping("/api/v1/user/exist")
    fun checkExistUser(){
        
    }

    // PUT

    // DELETE
}