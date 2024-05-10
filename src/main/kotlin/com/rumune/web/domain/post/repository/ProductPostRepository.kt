package com.rumune.web.domain.post.repository

import com.rumune.web.domain.post.entity.ProductPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ProductPostRepository : JpaRepository<ProductPost, UUID> {
    @Query("select distinct pp from ProductPost pp join pp.products ppp join ppp.product p join p.categories c where c.name =:category")
    fun findProductPostByCategoryName(category: String): List<ProductPost>
}
