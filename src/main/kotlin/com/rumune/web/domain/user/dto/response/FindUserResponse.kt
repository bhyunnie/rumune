package com.rumune.web.domain.user.dto.response

import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.domain.user.dto.UserDto
import com.rumune.web.global.enum.Responses

class FindUserResponse(
    override val message: String,
    override val status: Responses,
    override val result: UserDto,
) : CommonResponse<UserDto>
