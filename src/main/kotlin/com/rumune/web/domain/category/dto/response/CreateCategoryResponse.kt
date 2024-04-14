package com.rumune.web.domain.category.dto.response

import com.rumune.web.domain.category.dto.CategoryDto
import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.global.enum.Responses

class CreateCategoryResponse(
    override val message: String,
    override val status: Responses,
    override val result: CategoryDto
):CommonResponse<CategoryDto> {}