package com.trustprice.web.domain.post.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.domain.post.dto.ProductPostDto
import com.trustprice.web.global.enum.Responses

class CreateProductPostResponse(
    override val message: String,
    override val status: Responses,
    override val result: ProductPostDto,
) : CommonResponse<ProductPostDto>
