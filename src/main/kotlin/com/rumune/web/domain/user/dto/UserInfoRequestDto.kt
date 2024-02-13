package com.rumune.web.domain.user.dto

import com.rumune.web.domain.common.dto.CommonRequest
class UserInfoRequestDto(apiKey: String, userId: Long) : CommonRequest(apiKey) {}