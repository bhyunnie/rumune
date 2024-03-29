package com.rumune.web.domain.cart.application

import com.rumune.web.domain.cart.dto.request.AddProductToCartRequestProduct
import com.rumune.web.domain.cart.entity.Cart
import com.rumune.web.domain.cart.entity.CartProduct
import com.rumune.web.domain.cart.repository.CartProductRepository
import com.rumune.web.domain.cart.repository.CartRepository
import com.rumune.web.domain.product.entity.Product
import com.rumune.web.domain.product.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository
) {
    fun addProductToCart(userId:Long, productList:List<AddProductToCartRequestProduct>) {
        val productMap = productList.associateWith { it.count }.mapKeys { it.key.id }
        val cart = cartRepository.findByUserId(userId)

        val cartProductEntityList = productRepository.findProductList(productIdList = productList.map{it.id}).map{ product ->
            productMap[product.id]?.let {CartProduct(
                    cart =  cart[0],
                    product = product,
                    count = it
                )
            }
        }
         val result = cartProductRepository.saveAll(cartProductEntityList);
    }

    fun findUserCart (userId:Long):List<Cart> {
        return cartRepository.findByUserId(userId)
    }
}