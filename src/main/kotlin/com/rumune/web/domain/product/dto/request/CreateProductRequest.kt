package com.rumune.web.domain.product.dto.request

import org.springframework.web.multipart.MultipartFile

class CreateProductRequest(
    val files: List<MultipartFile>,
    val quantityLimit: Int,
    val price: Int,
    val name: String,
    val categoryId: Long,
)
