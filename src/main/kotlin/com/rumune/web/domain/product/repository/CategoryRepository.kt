package com.rumune.web.domain.product.repository

import com.rumune.web.domain.product.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository:JpaRepository<Category,Long> {
}