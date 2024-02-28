package com.rumune.web.domain.file.api

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.rumune.web.domain.common.enum.Responses
import com.rumune.web.domain.file.dto.FileUploadResponseDto
import com.rumune.web.global.properties.CloudProperties
import com.rumune.web.global.util.FileUtil
import org.apache.catalina.util.URLEncoder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@RestController
class FileApi(
    private val amazonS3Client: AmazonS3Client,
    private val cloudProperties: CloudProperties,
    private val fileUtil: FileUtil
) {
    @PostMapping("/api/v1/file/upload")
    fun uploadImage(@RequestParam("file") file:MultipartFile):ResponseEntity<FileUploadResponseDto> {
        val imageUrl = fileUtil.uploadToS3(file, "/temp")
        return ResponseEntity.ok(
            FileUploadResponseDto(
                Responses.OK,
                "업로드 완료",
                imageUrl
            )
        )
    }
}