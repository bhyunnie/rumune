package com.rumune.web.domain.product.dto.response

import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.domain.product.dto.ProductDto
import com.rumune.web.global.enum.Responses

class ProductListResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<ProductDto>,
) : CommonResponse<List<ProductDto>>
