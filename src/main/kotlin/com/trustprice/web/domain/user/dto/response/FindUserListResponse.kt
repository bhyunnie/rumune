package com.trustprice.web.domain.user.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.domain.user.dto.UserDto
import com.trustprice.web.global.enum.Responses

class FindUserListResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<UserDto>,
) : CommonResponse<List<UserDto>>
