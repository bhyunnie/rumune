package com.rumune.web.domain.product.repository

import com.rumune.web.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product,Long> {
//    fun findAllByJoinFetch():List<Product> // TODO joinFetch 구현
}