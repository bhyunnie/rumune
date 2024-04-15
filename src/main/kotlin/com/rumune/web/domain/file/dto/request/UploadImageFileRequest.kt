package com.rumune.web.domain.file.dto.request

import com.rumune.web.domain.file.enum.Domain
import org.springframework.web.multipart.MultipartFile

class UploadImageFileRequest(
    val file: MultipartFile,
    val domain: Domain
) {
}