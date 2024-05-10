package com.trustprice.web.domain.cart.dto.response

import com.trustprice.web.domain.cart.dto.CartDto
import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.global.enum.Responses

class FindCartResponse(
    override val message: String,
    override val status: Responses,
    override val result: CartDto,
) : CommonResponse<CartDto>
