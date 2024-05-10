package com.trustprice.web.domain.cart.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.trustprice.web.domain.user.entity.User
import jakarta.persistence.*

@Entity
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @OneToOne(cascade = [(CascadeType.REMOVE)])
    @JoinColumn(name = "user_id")
    val user: User,
    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    val product: List<CartProduct> = listOf(),
)
