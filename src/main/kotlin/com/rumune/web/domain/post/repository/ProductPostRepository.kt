package com.rumune.web.domain.post.repository

import com.rumune.web.domain.post.entity.ProductPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductPostRepository:JpaRepository<ProductPost,UUID>