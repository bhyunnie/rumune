package com.rumune.web.domain.category.dto.response

import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.global.enum.Responses


class CategoryResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<Category>
): CommonResponse<List<Category>> {

}