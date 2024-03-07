package com.rumune.web.domain.post.repository

import com.rumune.web.domain.post.entity.ProductPost
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ProductPostRepository:JpaRepository<ProductPost,UUID>