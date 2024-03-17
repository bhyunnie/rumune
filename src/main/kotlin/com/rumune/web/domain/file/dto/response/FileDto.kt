package com.rumune.web.domain.file.dto.response

import com.rumune.web.domain.file.entity.File

class FileDto(
    val fileURL:String
) {
    companion object {
        fun from (f:File):FileDto {
            return FileDto(
                fileURL = f.fileURL
            )
        }
    }
}