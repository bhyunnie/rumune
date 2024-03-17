package com.rumune.web.domain.user.dto

import com.rumune.web.global.enum.Responses

class CheckUserAuthority(
    val status: Responses,
    val message: String,
    val checked: Boolean,
)