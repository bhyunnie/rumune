package com.trustprice.web.domain.trend.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.global.enum.Responses

data class FindAllTrendResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<String>,
) : CommonResponse<List<String>>
