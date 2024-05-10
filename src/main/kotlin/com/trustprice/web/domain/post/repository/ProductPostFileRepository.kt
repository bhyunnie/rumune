package com.trustprice.web.domain.post.repository

import com.trustprice.web.domain.post.entity.ProductPostFile
import org.springframework.data.jpa.repository.JpaRepository

interface ProductPostFileRepository : JpaRepository<ProductPostFile, Long>
