package com.rumune.web.domain.category.repository

import com.rumune.web.domain.category.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, String>
