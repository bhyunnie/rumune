package com.trustprice.web.domain.cart.dto

import com.trustprice.web.domain.cart.entity.CartProduct

class CartProductDto(
    val name: String,
    val count: Long,
) {
    companion object {
        fun from(c: CartProduct): CartProductDto {
            return CartProductDto(
                name = c.product.name,
                count = c.count,
            )
        }
    }
}
