package com.rumune.web.domain.file.repository

import com.rumune.web.domain.product.entity.ProductImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductFileRepository:JpaRepository<ProductImage,Long>