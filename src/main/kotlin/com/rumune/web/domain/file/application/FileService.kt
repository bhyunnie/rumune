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

    private fun uploadToS3 (file: MultipartFile,fileKey:UUID, directory:String = ""):String {
        try {
        if(!checkIsImage(file)) throw Exception("is not file file")
        val path = if(directory == "") "" else removeSlash(directory)
        val bucketName = "${cloudProperties.aws.s3.bucket}/${path}"
        val ext = file.originalFilename?.substringAfter(".")?:throw Exception()
        val fileName = "${fileKey}.$ext"
            val inputStream: InputStream = file.inputStream
            val metadata = ObjectMetadata()
            metadata.contentType = file.contentType
            metadata.contentLength = file.size
            amazonS3Client.putObject(
                PutObjectRequest(bucketName, fileName, inputStream, metadata).withCannedAcl(
                    CannedAccessControlList.PublicRead))
            return amazonS3Client.getUrl(bucketName, fileName).toString()
        } catch (e : Exception) {
            throw e
        }
    }

    fun createFile(file:MultipartFile, userId:Long, directory:String):File {
        val fileUUID = UUID.randomUUID()
        val fileURL = uploadToS3(file,fileUUID,directory)

        return fileRepository.save(File(
            fileUUID = fileUUID,
            uploadUserId = userId,
            fileSize = file.size,
            fileURL = fileURL,
        ))
    }

    private fun removeSlash (directory: String):String {
        return directory.replace("/", "")
    }

    private fun checkIsImage (file:MultipartFile): Boolean {
        return file.contentType in arrayOf("image/jpeg", "image/png","file/jpg", "image/webp", "image/svg+xml",
            "image/bmp", "file/x-icon", "file/vnd.microsoft.icon")
    }
}