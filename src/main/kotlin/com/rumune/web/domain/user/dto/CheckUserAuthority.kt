package com.rumune.web.domain.user.dto

import com.rumune.web.domain.common.enum.Responses

class CheckUserAuthority(
    val status: Responses,
    val message: String,
    val checked: Boolean,
)