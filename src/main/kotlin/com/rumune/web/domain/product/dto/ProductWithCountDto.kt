package com.rumune.web.domain.product.dto

import com.rumune.web.domain.product.entity.Product
class ProductWithCountDto(
    override val id: Long,
    override val name: String,
    override val price: Int,
    override val quantityLimit: Int,
    override val thumbnail: String,
    override val stock: Int,
    val count: Long,
) :ProductCommonDto {
    companion object {
        fun from (
            c: Long,
            p: Product
        ): ProductWithCountDto {
            return ProductWithCountDto(
                id = p.id,
                name = p.name,
                price = p.price,
                quantityLimit = p.quantityLimit,
                thumbnail = p.image.sortedBy{ it.order }.first().file.fileURL,
                stock = p.stock,
                count = c
            )
        }
    }
}