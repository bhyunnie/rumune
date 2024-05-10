package com.trustprice.web.domain.product.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.domain.product.dto.ProductDto
import com.trustprice.web.global.enum.Responses

class ProductListResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<ProductDto>,
) : CommonResponse<List<ProductDto>>
