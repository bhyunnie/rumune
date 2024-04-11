package com.rumune.web.domain.cart.application

import com.rumune.web.domain.cart.dto.request.AddProductToCartRequestProduct
import com.rumune.web.domain.cart.entity.Cart
import com.rumune.web.domain.cart.entity.CartProduct
import com.rumune.web.domain.cart.repository.CartProductRepository
import com.rumune.web.domain.cart.repository.CartRepository
import com.rumune.web.domain.product.repository.ProductRepository
import com.rumune.web.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
    private val userRepository: UserRepository
) {
    fun addProductToCart(userId:Long, productList:List<AddProductToCartRequestProduct>):List<CartProduct> {
        val productMap = productList.associateWith { it.count }.mapKeys { it.key.id }.toMutableMap()
        var cart = cartRepository.findByUserId(userId)
        val cartProductEntityList = mutableListOf<CartProduct>()
        if(cart.isEmpty()) {
             cart = listOf(cartRepository.save(Cart(user = userRepository.findById(userId).get())))
        }
        if (cart[0].product.isNotEmpty()) {
            cart[0].product.forEach {
                it.count += productMap[it.product.id] ?: 0
                cartProductEntityList.add(it)
                productMap.remove(it.product.id)
            }
        }
        productRepository.findProductList(productIdList = productMap.map{it.key}).forEach{ p->
            productMap[p.id]?.let {
                cartProductEntityList.add(CartProduct(cart = cart[0], product = p, count = it))
            }
        }
        val result = cartProductRepository.saveAll(cartProductEntityList);
        return result
    }

    fun findUserCart (userId:Long):List<Cart> {
        return cartRepository.findByUserId(userId)
    }
}