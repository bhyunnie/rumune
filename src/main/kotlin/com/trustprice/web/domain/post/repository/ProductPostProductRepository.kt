package com.trustprice.web.domain.post.repository

import com.trustprice.web.domain.post.entity.ProductPostProduct
import org.springframework.data.jpa.repository.JpaRepository

interface ProductPostProductRepository : JpaRepository<ProductPostProduct, Long>
