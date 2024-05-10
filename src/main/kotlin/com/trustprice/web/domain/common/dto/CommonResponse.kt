package com.trustprice.web.domain.common.dto

import com.trustprice.web.global.enum.Responses

interface CommonResponse<T> {
    val message: String
    val status: Responses
    val result: T
}
