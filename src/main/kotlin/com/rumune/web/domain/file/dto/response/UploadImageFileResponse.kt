package com.rumune.web.domain.file.dto.response

import com.rumune.web.domain.common.dto.CommonResponse
import com.rumune.web.global.enum.Responses

class UploadImageFileResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<FileDto>
):CommonResponse<List<FileDto>> {
}