package com.rumune.web.domain.cart.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.rumune.web.domain.user.entity.User
import jakarta.persistence.*

@Entity
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name="user_id")
    val user: User,

    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    val product:List<CartProduct>? = null,
) {
}