package com.rumune.web.domain.file.api

import com.rumune.web.domain.common.enum.Responses
import com.rumune.web.domain.file.application.FileService
import com.rumune.web.domain.file.dto.FileUploadResponseDto
import com.rumune.web.domain.file.dto.PostFileUploadRequestDto
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FileApi(
    private val fileService: FileService,
    private val validateUtil: ValidateUtil
) {
    @PostMapping("/api/v1/file/upload/post")
    fun uploadImage(@ModelAttribute postFileUploadRequestDto: PostFileUploadRequestDto, request:HttpServletRequest):ResponseEntity<FileUploadResponseDto> {
        val userId = validateUtil.extractUserIdFromBearerToken(request)
        val imageUrl = fileService.uploadToS3(postFileUploadRequestDto.file, "/temp", userId=userId)
        return ResponseEntity.ok(
            FileUploadResponseDto(
                Responses.OK,
                "업로드 완료",
                imageUrl
            )
        )
    }
}