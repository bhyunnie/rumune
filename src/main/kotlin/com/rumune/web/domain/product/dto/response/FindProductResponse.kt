package com.rumune.web.domain.product.dto.response

import com.rumune.web.domain.product.entity.Product
import com.rumune.web.global.util.DateUtil

class FindProductResponse(
    val id: Long,
    val name:String,
    val price: Int,
    val quantityLimit: Int,
    val createdAt: String,
    val category: List<String>,
    val image: List<String>,
    val thumbnail: String,
) {
    companion object {
        fun from (
            p:Product
        ): FindProductResponse {
            return FindProductResponse(
                id = p.id,
                name = p.name,
                price = p.price,
                quantityLimit = p.quantityLimit,
                createdAt = DateUtil().offsetDateTimeToYYYYMMDD(p.createdAt),
                category = p.categories.map {it.name},
                image = p.image.sortedBy { it.order }.map{it.image.fileURL},
                thumbnail = p.image.sortedBy{ it.order }.first().image.fileURL
            )
        }
    }
}