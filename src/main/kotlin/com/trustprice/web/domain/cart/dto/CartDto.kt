package com.trustprice.web.domain.cart.dto

import com.trustprice.web.domain.cart.entity.Cart
import com.trustprice.web.domain.product.dto.ProductWithCountDto

class CartDto(
    val productList: List<ProductWithCountDto>?,
) {
    companion object {
        fun from(c: Cart): CartDto {
            return CartDto(
                productList =
                    if (c.product.isEmpty()) {
                        null
                    } else {
                        c.product.map {
                            ProductWithCountDto.from(
                                c = it.count,
                                p = it.product,
                            )
                        }
                    },
            )
        }
    }
}
