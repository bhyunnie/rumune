package com.rumune.web.domain.post.dto.request

import org.springframework.web.multipart.MultipartFile

data class CreateProductPostRequest(
    val thumbnail:MultipartFile,
    val title:String,
    val discount: Int,
    val deliveryFee: Int,
    val productIdList: List<Long>,
    val content:String,
    val domain:String,
    val postImageURLList: List<String>
) {
}