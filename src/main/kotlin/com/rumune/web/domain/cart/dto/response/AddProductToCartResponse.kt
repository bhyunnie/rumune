package com.rumune.web.domain.cart.dto.response

import com.rumune.web.domain.cart.dto.CartProductDto
import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.global.enum.Responses

class AddProductToCartResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<CartProductDto>,
) : CommonResponse<List<CartProductDto>>
