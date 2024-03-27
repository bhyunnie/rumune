package com.rumune.web.domain.product.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.rumune.web.domain.file.entity.File
import jakarta.persistence.*

@Entity
@Table(name ="product_image")
class ProductImage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long=0,

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    val product: Product,

    @ManyToOne
    @JoinColumn(name = "file_uuid")
    val file: File,

    @Column(name ="order_num")
    var order:Int =0
) {

}