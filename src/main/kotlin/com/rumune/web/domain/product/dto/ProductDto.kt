package com.rumune.web.domain.product.dto

import com.rumune.web.domain.product.entity.Product
import com.rumune.web.global.util.DateUtil

class ProductDto(
    val id: Long,
    val name:String,
    val price: Int,
    val quantityLimit: Int,
    val createdAt: String,
    val category: List<String>,
    val image: List<String>,
    val thumbnail: String,
    val stock: Int,
) {
    companion object {
        fun from (
            p:Product
        ): ProductDto {
            return ProductDto(
                id = p.id,
                name = p.name,
                price = p.price,
                quantityLimit = p.quantityLimit,
                createdAt = DateUtil().offsetDateTimeToYYYYMMDD(p.createdAt),
                category = p.categories.map {it.name},
                image = p.image.sortedBy { it.order }.map{it.file.fileURL},
                thumbnail = p.image.sortedBy{ it.order }.first().file.fileURL,
                stock = p.stock
            )
        }
    }
}