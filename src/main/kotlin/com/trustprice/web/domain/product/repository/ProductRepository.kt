package com.trustprice.web.domain.product.repository

import com.trustprice.web.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id in :productIdList")
    fun findProductList(productIdList: List<Long>): List<Product>
}
