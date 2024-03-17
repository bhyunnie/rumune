package com.rumune.web.domain.post.dto.request

import com.rumune.web.domain.file.entity.File

class CreateProductPostRequest(
    title:String,
    content:String,
    file:File,
    productIdList: List<Long>
) {
}