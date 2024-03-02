package com.rumune.web.domain.product.repository

import com.rumune.web.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductRepository: JpaRepository<Product,UUID> {
}