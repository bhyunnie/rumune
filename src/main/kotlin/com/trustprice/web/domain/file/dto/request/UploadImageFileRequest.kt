package com.trustprice.web.domain.file.dto.request

import com.trustprice.web.domain.file.enum.Domain
import org.springframework.web.multipart.MultipartFile

class UploadImageFileRequest(
    val file: MultipartFile,
    val domain: Domain,
)
