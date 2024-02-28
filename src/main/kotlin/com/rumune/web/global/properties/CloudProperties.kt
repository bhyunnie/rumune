package com.rumune.web.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloud")
data class CloudProperties(
    val aws: Aws
) {
    data class Aws(
        val stack:AwsStackProperties,
        val region: AwsRegion,
        val credentials: AwsCredentialsProperties,
        val s3: S3
    )

    data class AwsStackProperties(
        val auto: Boolean
    )

    data class AwsCredentialsProperties(
        val accessKey: String,
        val secretKey: String
    )

    data class S3(
        val bucket: String
    )

    data class AwsRegion(
        val static: String
    )
}