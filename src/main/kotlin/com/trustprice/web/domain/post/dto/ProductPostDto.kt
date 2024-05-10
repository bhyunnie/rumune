package com.trustprice.web.domain.post.dto

import com.trustprice.web.domain.post.entity.ProductPost
import com.trustprice.web.domain.product.entity.Product
import java.util.UUID

class ProductPostDto(
    val uuid: UUID,
    val title: String,
    val content: String,
    val discount: Double,
    val deliveryFee: Int,
    val isPosted: Boolean,
    val thumbnailURL: String,
    val createdBy: String,
    val images: List<String>,
    val products: List<Product>,
    val createdAt: String,
) {
    companion object {
        fun from(p: ProductPost): ProductPostDto {
            return ProductPostDto(
                uuid = p.uuid,
                title = p.title,
                content = p.content,
                discount = p.discount,
                deliveryFee = p.deliveryFee,
                isPosted = p.isPosted,
                thumbnailURL = p.thumbnailURL,
                createdBy = p.createdBy.name,
                images = if (p.image.isEmpty()) listOf() else p.image.map { it.file.fileURL },
                products = if (p.products.isEmpty()) listOf() else p.products.map { it.product },
                createdAt = p.createdAt.toString(),
            )
        }
    }
}
