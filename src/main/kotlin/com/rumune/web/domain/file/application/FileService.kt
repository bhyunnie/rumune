package com.rumune.web.domain.file.application

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.rumune.web.domain.file.entity.File
import com.rumune.web.domain.file.repository.FileRepository
import com.rumune.web.global.properties.CloudProperties
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.*

@Service
class FileService(
    private val fileRepository: FileRepository,
    private val cloudProperties: CloudProperties,
    private val amazonS3Client: AmazonS3Client,
) {

    /**
     * 파일을 S3 에 업로드 (단건)
     */
    private fun uploadImageToS3 (file: MultipartFile,fileKey:UUID, directory:String = ""):String {
        try {
            // 파일 확장자 체크
            if(!checkIsImage(file)) throw Exception("이미지 파일이 아닙니다.")
            // 디렉토리 넣을 때 슬래쉬를 넣을 수도 안넣을 수도 있게 (편의성)
            val path = if(directory == "") "" else removeSlash(directory)
            val bucketName = "${cloudProperties.aws.s3.bucket}/${path}"
            // 확장자에 . 이 여러개 있는 case 대응
            val ext = file.originalFilename?.split(".")?.last() ?: ""
            val fileName = "${fileKey}.$ext"
            val inputStream: InputStream = file.inputStream
            val metadata = ObjectMetadata().apply {
                contentType = file.contentType
                contentLength = file.size
            }

            amazonS3Client.putObject(
                PutObjectRequest(bucketName, fileName, inputStream, metadata).withCannedAcl(
                    CannedAccessControlList.PublicRead))
            return amazonS3Client.getUrl(bucketName, fileName).toString()
        } catch (e : Exception) {
            throw e
        }
    }

    /**
     * 파일 업로드 (단건)
     * TODO 수정 필요, 다건 용 파일 업로드 로직 필요
     */
    fun createFile(file: MultipartFile, userId: Long, directory: String): File {
        val fileUUID = UUID.randomUUID()
        val fileURL = uploadImageToS3(file, fileUUID, directory)

        return fileRepository.save(
            File(
                fileUUID = fileUUID,
                uploadUserId = userId,
                fileSize = file.size,
                fileURL = fileURL,
            )
        )
    }

    private fun removeSlash (directory: String):String {
        return directory.replace("/", "")
    }

    private fun checkIsImage (file:MultipartFile): Boolean {
        return file.contentType in arrayOf("image/jpeg", "image/png","file/jpg", "image/webp", "image/svg+xml",
            "image/bmp", "file/x-icon", "file/vnd.microsoft.icon")
    }
}