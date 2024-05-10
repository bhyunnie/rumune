package com.trustprice.web.domain.user.dto.response

import com.trustprice.web.domain.user.dto.UserCountHistoryDto
import com.trustprice.web.global.enum.Responses

class FindUserHistoryResponse(
    val status: Responses,
    val message: String,
    val responseData: List<UserCountHistoryDto>,
)
