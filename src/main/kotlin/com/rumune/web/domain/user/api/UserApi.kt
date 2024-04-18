package com.rumune.web.domain.user.api

import com.rumune.web.global.enum.Responses
import com.rumune.web.domain.user.application.UserHistoryService
import com.rumune.web.domain.user.application.UserService
import com.rumune.web.domain.user.dto.*
import com.rumune.web.domain.user.dto.request.AuthenticationRequest
import com.rumune.web.domain.user.dto.request.GetUserHistoryRequest
import com.rumune.web.domain.user.dto.response.*
import com.rumune.web.global.extensionFunctions.getUserId
import com.rumune.web.global.util.CookieUtil
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserApi(
    private val userService: UserService,
    private val userHistoryService: UserHistoryService,
    private val cookieUtil: CookieUtil,
) {
    /**
     * 전체 유저 조회 (다건)
     */
    @GetMapping("/api/v1/user/list")
    fun findUserList(): ResponseEntity<FindUserListResponse> {
        val userList = userService.findAll()
        return ResponseEntity.ok()
            .body(FindUserListResponse("전체 유저 조회 완료", Responses.OK, userList.map{UserDto.from(it)})
        )
    }
    /**
     * 유저 조회 (단건)
     */
    @GetMapping("/api/v1/user/{id}")
    fun findUserByUserId(@PathVariable id: Long): ResponseEntity<FindUserResponse> {
        val user = userService.findUserById(id)
        return ResponseEntity.ok()
            .body(FindUserResponse("유저 조회 완료", Responses.OK, UserDto.from(user)))
    }
    /**
     * 토큰을 통한 유저 정보 조회 (단건)
     */
    @GetMapping("/api/v1/user/me")
    fun findUserProfile(hsr:HttpServletRequest):ResponseEntity<FindUserResponse> {
        val user = userService.findUserById(hsr.getUserId())
        return ResponseEntity.ok()
            .body(FindUserResponse("유저 조회 완료", Responses.OK, UserDto.from(user)))
    }
    /**
     * 어드민 권한 검증 
     */
    @GetMapping("/api/v1/admin/check/authority")
    fun checkAdminAuthority(hsr:HttpServletRequest):ResponseEntity<CheckUserAuthorityResponse> {
        val user = userService.findUserById(hsr.getUserId())
        val result = userService.checkAuthority(user.email, "ROLE_ADMIN")
        return ResponseEntity.ok()
            .body(CheckUserAuthorityResponse("권한 확인 완료",Responses.OK, result))
    }
    /**
     * 유저 수 조회
     */
    @GetMapping("/api/v1/admin/user/count")
    fun getUserCount(@ModelAttribute getUserHistoryRequest: GetUserHistoryRequest):ResponseEntity<FindUserHistoryResponse> {
        val response = userHistoryService.getUserCountHistory(getUserHistoryRequest.date)
        return ResponseEntity.ok(
            FindUserHistoryResponse(
                status= Responses.OK,
                message = "완료",
                responseData = response.map{ UserCountHistoryDto.from(it)}
            )

        )
    }
    /**
     * 자격검증
     */
    @PostMapping("/api/v1/signin")
    fun authenticate(
        @RequestBody authenticationRequest: AuthenticationRequest,
        httpServletResponse: HttpServletResponse,
    ): AuthenticationResponse {
        val authenticationResponse = userService.authentication(authenticationRequest)
        val cookie = cookieUtil.createAccessTokenCookie(authenticationResponse.accessToken)
        httpServletResponse.addCookie(cookie)
        return authenticationResponse
    }
}