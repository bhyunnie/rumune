package com.rumune.web.domain.cart.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.rumune.web.domain.product.entity.Product
import jakarta.persistence.*

@Entity
@Table(name="cart_product")
class CartProduct(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,

    @ManyToOne
    @JoinColumn(name="cart_id")
    @JsonBackReference
    val cart:Cart,

    val count:Int,

    @ManyToOne
    @JoinColumn(name="product_id")
    val product:Product
) {
}