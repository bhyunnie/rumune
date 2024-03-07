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
    fun uploadToS3 (file: MultipartFile, directory:String = "", userId:Long):String {
        try {
        val path = if(directory == "") "" else removeSlash(directory)
        val bucketName = "${cloudProperties.aws.s3.bucket}/${path}"
        val ext = file.originalFilename?.substringAfter(".")?:throw Exception()
        val fileKey = UUID.randomUUID()
        val fileName = "${fileKey}.$ext"
            val inputStream: InputStream = file.inputStream
            val metadata = ObjectMetadata()
            metadata.contentType = file.contentType
            metadata.contentLength = file.size
            amazonS3Client.putObject(
                PutObjectRequest(bucketName, fileName, inputStream, metadata).withCannedAcl(
                    CannedAccessControlList.PublicRead))
            fileRepository.save(
                File(fileUUID = fileKey, ext=ext, bucketName=bucketName, fileSize=file.size, uploadUserId=userId)
            )
            return amazonS3Client.getUrl(bucketName, fileName).toString()
        } catch (e : Exception) {
            throw e
        }
    }

    private fun removeSlash (directory: String):String {
        return directory.replace("/", "")
    }
}