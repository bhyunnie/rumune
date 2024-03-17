package com.rumune.web.domain.jwt.dto.response

import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.global.enum.Responses

class RefreshAccessTokenResponse(
    override val message: String,
    override val status: Responses,
    override val result: Boolean
):CommonResponse<Boolean> {
}