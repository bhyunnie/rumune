package com.trustprice.web.domain.file.api

import com.trustprice.web.domain.file.application.FileService
import com.trustprice.web.domain.file.dto.FileDto
import com.trustprice.web.domain.file.dto.request.UploadImageFileRequest
import com.trustprice.web.domain.file.dto.request.UploadMultiImageFileRequest
import com.trustprice.web.domain.file.dto.response.UploadImageFileResponse
import com.trustprice.web.domain.file.dto.response.UploadMultiImageFileResponse
import com.trustprice.web.global.enum.Responses
import com.trustprice.web.global.extensionFunctions.getUserId
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FileApi(
    private val fileService: FileService,
) {
    /**
     * 이미지 업로드 (insert, 단건)
     */
    @PostMapping("/admin/api/v1/image/upload")
    fun uploadImage(
        @ModelAttribute request: UploadImageFileRequest,
        hsr: HttpServletRequest,
    ): ResponseEntity<UploadImageFileResponse> {
        val file = fileService.createFile(request.file, hsr.getUserId(), request.domain.name)
        return ResponseEntity.ok(
            UploadImageFileResponse(
                message = "이미지 업로드 성공",
                status = Responses.OK,
                result = FileDto.from(file),
            ),
        )
    }

    /**
     * 이미지 업로드 (insert, 다건)
     */
    @PostMapping("/admin/api/v1/image/upload/multi")
    fun uploadMultiImage(
        @ModelAttribute request: UploadMultiImageFileRequest,
        hsr: HttpServletRequest,
    ): ResponseEntity<UploadMultiImageFileResponse> {
        val fileList = request.files.map { fileService.createFile(it, hsr.getUserId(), request.domain.name) }
        return ResponseEntity.ok(
            UploadMultiImageFileResponse(
                message = "다중 이미지 업로드 성공",
                status = Responses.OK,
                result = fileList.map { FileDto.from(it) },
            ),
        )
    }
}
