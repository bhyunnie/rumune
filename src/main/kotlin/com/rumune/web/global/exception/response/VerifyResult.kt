package com.rumune.web.global.exception.response

data class VerifyResult(
    val success: Boolean,
    val username: String? = null,
) {

}