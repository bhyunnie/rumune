package com.rumune.web.domain.post.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.rumune.web.domain.product.entity.Product
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name ="product_post_product")
class ProductPostProduct(
    @Id
    val id:Long,

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="product_post_uuid")
    val productPost: ProductPost,

    @ManyToOne
    @JoinColumn(name="product_id")
    val product: Product
) {
}