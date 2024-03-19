package com.rumune.web.domain.category.entity

import jakarta.persistence.*

@Entity
@Table(name="category")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name="name", nullable = false, unique = true)
    val name: String = "",

    @Column(name="english_name", nullable = false, unique = true)
    val englishName: String = "",
) {
}