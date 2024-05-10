package com.trustprice.web.domain.cart.dto.response

import com.trustprice.web.domain.cart.dto.CartProductDto
import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.global.enum.Responses

class AddProductToCartResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<CartProductDto>,
) : CommonResponse<List<CartProductDto>>
