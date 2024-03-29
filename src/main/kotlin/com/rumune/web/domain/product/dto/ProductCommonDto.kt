package com.rumune.web.domain.product.dto

interface ProductCommonDto {
    val id: Long
    val name:String
    val price: Int
    val quantityLimit: Int
    val thumbnail: String
    val stock: Int
}