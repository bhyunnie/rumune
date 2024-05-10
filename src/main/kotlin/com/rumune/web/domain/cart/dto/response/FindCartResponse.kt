package com.rumune.web.domain.cart.dto.response

import com.rumune.web.domain.cart.dto.CartDto
import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.global.enum.Responses

class FindCartResponse(
    override val message: String,
    override val status: Responses,
    override val result: CartDto,
) : CommonResponse<CartDto>
