package com.rumune.web.domain.post.repository

import com.rumune.web.domain.post.entity.ProductPostProduct
import org.springframework.data.jpa.repository.JpaRepository

interface ProductPostProductRepository:JpaRepository<ProductPostProduct,Long> {
}