package com.rumune.web.domain.user.dto

import com.rumune.web.domain.user.entity.User
import com.rumune.web.domain.common.enum.Responses

class UserInfoResponseDto (
    val status: Responses,
    val message: String,
    val userList: List<UserDto>
)