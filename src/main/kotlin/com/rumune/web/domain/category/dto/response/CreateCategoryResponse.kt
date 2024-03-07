package com.rumune.web.domain.category.dto.response

import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.domain.common.enum.Responses

class CreateCategoryResponse(
    override val message: String,
    override val status: Responses,
    override val result: Boolean
):CommonResponse<Boolean> {}