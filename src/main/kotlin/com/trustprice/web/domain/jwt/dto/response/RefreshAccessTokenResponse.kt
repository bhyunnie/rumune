package com.trustprice.web.domain.jwt.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.global.enum.Responses

class RefreshAccessTokenResponse(
    override val message: String,
    override val status: Responses,
    override val result: Boolean
):CommonResponse<Boolean> {
}