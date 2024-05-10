package com.rumune.web.domain.common.dto

import com.rumune.web.global.enum.Responses

interface CommonResponse<T> {
    val message: String
    val status: Responses
    val result: T
}
