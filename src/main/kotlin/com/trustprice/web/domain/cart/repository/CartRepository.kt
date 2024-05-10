package com.trustprice.web.domain.cart.repository

import com.trustprice.web.domain.cart.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CartRepository : JpaRepository<Cart, Long> {
    fun findByUserId(userId: Long): Optional<Cart>
}
