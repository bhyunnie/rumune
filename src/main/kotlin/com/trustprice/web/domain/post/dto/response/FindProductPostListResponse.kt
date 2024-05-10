package com.trustprice.web.domain.post.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.domain.post.dto.ProductPostDto
import com.trustprice.web.global.enum.Responses

class FindProductPostListResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<ProductPostDto>,
) : CommonResponse<List<ProductPostDto>>
