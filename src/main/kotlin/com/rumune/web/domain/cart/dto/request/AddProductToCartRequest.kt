package com.rumune.web.domain.cart.dto.request

class AddProductToCartRequestProduct(
    val id: Long,
    val count: Long,
) {

}
class AddProductToCartRequest(
    val productList: List<AddProductToCartRequestProduct>
) {
}