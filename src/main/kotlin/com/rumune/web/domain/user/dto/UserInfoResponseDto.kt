package com.rumune.web.domain.user.dto

import com.rumune.web.domain.user.domain.User
import com.rumune.web.global.common.response.Responses

class UserInfoResponseDto (
    val response: Responses,
    val userList: List<User>
)