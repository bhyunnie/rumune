package com.rumune.web.domain.user.dto

import com.rumune.web.global.enum.Responses
import com.rumune.web.domain.user.entity.UserCountHistory

class GetUserHistoryResponseDto(
    val status: Responses,
    val message: String,
    val responseData: List<UserCountHistoryDto>
) {
}