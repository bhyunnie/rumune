package com.rumune.web.global.util

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.rumune.web.global.properties.CloudProperties
import org.apache.catalina.util.URLEncoder
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.UUID

@Component
class FileUtil(
    private val cloudProperties: CloudProperties,
    private val amazonS3Client: AmazonS3Client
) {
    fun uploadToS3 (file:MultipartFile, directory:String = ""):String {
        val path = if(directory == "") "" else removeSlash(directory)
        val bucketName = "${cloudProperties.aws.s3.bucket}/${path}"
        val ext = file.originalFilename?.substringAfter(".")
        val fileName = "${UUID.randomUUID()}.$ext"

        try {
            val inputStream: InputStream = file.inputStream
            val metadata = ObjectMetadata()
            metadata.contentType = file.contentType
            metadata.contentLength = file.size
            amazonS3Client.putObject(PutObjectRequest(bucketName, fileName, inputStream, metadata).withCannedAcl(CannedAccessControlList.PublicRead))
        } catch (e : Exception) {
            throw e
        }

        return amazonS3Client.getUrl(bucketName, fileName).toString()
    }

    private fun removeSlash (directory: String):String {
        return directory.replace("/", "")
    }
}