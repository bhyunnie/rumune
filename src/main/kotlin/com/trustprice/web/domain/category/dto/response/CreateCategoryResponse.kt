package com.trustprice.web.domain.category.dto.response

import com.trustprice.web.domain.category.dto.CategoryDto
import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.global.enum.Responses

class CreateCategoryResponse(
    override val message: String,
    override val status: Responses,
    override val result: CategoryDto,
) : CommonResponse<CategoryDto>
