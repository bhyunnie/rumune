package com.rumune.web.domain.product.dto.response

import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.domain.common.enum.Responses
import com.rumune.web.domain.product.entity.Product

class ProductResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<Product>
):CommonResponse<List<Product>> {
}