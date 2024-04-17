package com.rumune.web.domain.user.dto.response

import com.rumune.web.domain.user.dto.UserCountHistoryDto
import com.rumune.web.global.enum.Responses

class FindUserHistoryResponse(
    val status: Responses,
    val message: String,
    val responseData: List<UserCountHistoryDto>
) {
}