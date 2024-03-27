package com.rumune.web.domain.cart.application

import com.rumune.web.domain.cart.entity.Cart
import com.rumune.web.domain.cart.repository.CartRepository
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository
) {
    fun findUserCart (userId:Long):List<Cart> {
        return cartRepository.findByUserId(userId)
    }
}