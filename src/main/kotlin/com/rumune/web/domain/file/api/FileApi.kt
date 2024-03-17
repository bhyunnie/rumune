package com.rumune.web.domain.file.api

import com.rumune.web.domain.file.application.FileService
import com.rumune.web.domain.file.dto.request.UploadImageFileRequest
import com.rumune.web.domain.file.dto.request.UploadMultiImageFileRequest
import com.rumune.web.domain.file.dto.response.FileDto
import com.rumune.web.domain.file.dto.response.UploadImageFileResponse
import com.rumune.web.domain.file.dto.response.UploadMultiImageFileResponse
import com.rumune.web.global.enum.Responses
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FileApi(
    private val validateUtil: ValidateUtil,
    private val fileService: FileService,
) {
    /**
     * 이미지 업로드
     */
    @PostMapping("/admin/api/v1/image/upload")
    fun uploadImage(@ModelAttribute request:UploadImageFileRequest,hsr: HttpServletRequest):ResponseEntity<UploadImageFileResponse> {
        val userId = validateUtil.extractUserIdFromBearerToken(hsr)
        val file = fileService.createFile(request.file, userId, request.domain.name)

        return ResponseEntity.ok(UploadImageFileResponse(
            message = "이미지 업로드 성공",
            status = Responses.OK,
            result = listOf(FileDto.from(file))
        ))
    }

    /**
     * 다중 이미지 업로드
     */
    @PostMapping("/admin/api/v1/image/upload/multi")
    fun uploadMultiImage(@ModelAttribute request:UploadMultiImageFileRequest, hsr: HttpServletRequest):ResponseEntity<UploadMultiImageFileResponse> {
        val userId = validateUtil.extractUserIdFromBearerToken(hsr)
        val fileList = request.files.map{ file ->
            fileService.createFile(file, userId, request.domain.name)
        }

        return ResponseEntity.ok(UploadMultiImageFileResponse(
            message = "다중 이미지 업로드 성공",
            status = Responses.OK,
            result = fileList.map{file -> FileDto.from(file)}
        ))
    }


}