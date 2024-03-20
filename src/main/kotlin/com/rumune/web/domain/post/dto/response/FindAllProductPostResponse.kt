package com.rumune.web.domain.post.dto.response

import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.domain.post.dto.ProductPostDto
import com.rumune.web.global.enum.Responses

class FindAllProductPostResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<ProductPostDto>
):CommonResponse<List<ProductPostDto>> {

}