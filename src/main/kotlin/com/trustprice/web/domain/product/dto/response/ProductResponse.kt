package com.trustprice.web.domain.product.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.domain.product.dto.ProductDto
import com.trustprice.web.global.enum.Responses

class ProductResponse(
    override val message: String,
    override val status: Responses,
    override val result: ProductDto,
) : CommonResponse<ProductDto>
