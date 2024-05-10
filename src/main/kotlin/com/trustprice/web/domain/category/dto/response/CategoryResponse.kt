package com.trustprice.web.domain.category.dto.response

import com.trustprice.web.domain.category.dto.CategoryDto
import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.global.enum.Responses

class CategoryResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<CategoryDto>,
) : CommonResponse<List<CategoryDto>>
