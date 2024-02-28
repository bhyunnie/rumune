package com.rumune.web.domain.file.dto

import com.rumune.web.domain.common.enum.Responses

class FileUploadResponseDto (
    val status: Responses,
    val message: String,
    val url: String,
) {

}