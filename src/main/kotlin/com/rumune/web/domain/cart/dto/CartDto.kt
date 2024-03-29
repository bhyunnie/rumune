package com.rumune.web.domain.cart.dto

import com.rumune.web.domain.cart.entity.Cart
import com.rumune.web.domain.product.dto.ProductWithCountDto

class CartDto(
    val productList:List<ProductWithCountDto>?
) {
    companion object {
        fun from (c:Cart):CartDto {
            return CartDto(
                productList = if (c.product == null) null else c.product.map{
                    ProductWithCountDto.from(
                        c = it.count,
                        p = it.product
                    )
                }
            )
        }
    }
}