package com.rumune.web.domain.cart.repository

import com.rumune.web.domain.cart.entity.CartProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartProductRepository : JpaRepository<CartProduct, Long>
