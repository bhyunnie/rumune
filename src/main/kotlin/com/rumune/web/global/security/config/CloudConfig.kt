package com.rumune.web.global.security.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.rumune.web.global.properties.CloudProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CloudConfig(
    private val cloudProperties: CloudProperties,
) {
    private val accessKey: String = cloudProperties.aws.credentials.accessKey
    private val secretKey: String = cloudProperties.aws.credentials.secretKey
    private val region: String = cloudProperties.aws.region.static

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val basicAWSCredentials: BasicAWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonS3ClientBuilder.standard().withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(basicAWSCredentials)).build() as AmazonS3Client
    }
}
