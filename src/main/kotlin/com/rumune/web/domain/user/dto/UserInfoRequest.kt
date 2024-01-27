package com.rumune.web.domain.user.dto

import com.rumune.web.global.common.request.CommonRequest
class UserInfoRequest(override val apiKey: String, val userId: String) : CommonRequest