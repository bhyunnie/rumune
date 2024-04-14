package com.rumune.web.domain.cart.dto.request

/**
 * 추가할 상품 리스트를
 */
class AddProductToCartRequest(
    val productList: List<AddProductToCartRequestProduct>
)