package com.trustprice.web.domain.file.dto.response

import com.trustprice.web.domain.common.dto.CommonResponse
import com.trustprice.web.domain.file.dto.FileDto
import com.trustprice.web.global.enum.Responses

class UploadMultiImageFileResponse(
    override val message: String,
    override val status: Responses,
    override val result: List<FileDto>,
) : CommonResponse<List<FileDto>>
