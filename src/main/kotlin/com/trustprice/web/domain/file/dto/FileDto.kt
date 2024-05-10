package com.trustprice.web.domain.file.dto

import com.trustprice.web.domain.file.entity.File

class FileDto(
    val fileURL: String,
) {
    companion object {
        fun from(f: File): FileDto {
            return FileDto(
                fileURL = f.fileURL,
            )
        }
    }
}
