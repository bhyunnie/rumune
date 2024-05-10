package com.trustprice.web.domain.category.repository

import com.trustprice.web.domain.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, String>
