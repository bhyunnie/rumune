package com.rumune.web.domain.product.entity

import jakarta.persistence.*

@Entity
@Table(name = "productOption")
class ProductOption(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val price: Int = 0,

    @Column(nullable = false)
    val isSoldOut: Boolean = false
) {
}