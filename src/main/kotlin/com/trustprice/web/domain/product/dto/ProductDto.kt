package com.trustprice.web.domain.product.dto

import com.trustprice.web.domain.product.entity.Product
import com.trustprice.web.global.util.DateUtil

class ProductDto(
    override val id: Long,
    override val name: String,
    override val price: Int,
    override val quantityLimit: Int,
    val createdAt: String,
    val category: List<String>,
    val image: List<String>,
    override val thumbnail: String,
    override val stock: Int,
) : ProductCommonDto {
    companion object {
        fun from(p: Product): ProductDto {
            return ProductDto(
                id = p.id,
                name = p.name,
                price = p.price,
                quantityLimit = p.quantityLimit,
                createdAt = DateUtil().offsetDateTimeToYYYYMMDD(p.createdAt),
                category = p.categories.map { it.name },
                image = p.image.sortedBy { it.order }.map { it.file.fileURL },
                thumbnail = p.image.sortedBy { it.order }.first().file.fileURL,
                stock = p.stock,
            )
        }
    }
}
