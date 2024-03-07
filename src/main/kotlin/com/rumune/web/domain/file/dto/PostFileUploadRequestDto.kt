package com.rumune.web.domain.file.dto

import org.springframework.web.multipart.MultipartFile

class PostFileUploadRequestDto(
    val file: MultipartFile,
) {
}