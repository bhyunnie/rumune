package com.rumune.web.domain.product.entity

import jakarta.persistence.*

@Entity
@Table(name="category")
class Category(
    @Id
    val name: String
) {
}