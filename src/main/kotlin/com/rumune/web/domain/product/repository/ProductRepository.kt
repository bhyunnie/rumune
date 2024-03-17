package com.rumune.web.domain.product.repository

import com.rumune.web.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository: JpaRepository<Product,Long> {
    @Query("select p from Product p left join ProductImage pi on p.id = pi.product.id " +
            "left join File f on pi.image.fileUUID = f.fileUUID")
    fun findAllByJoinFetch():List<Product> // TODO joinFetch 구현
}