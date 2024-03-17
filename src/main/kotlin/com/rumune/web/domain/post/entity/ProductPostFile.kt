package com.rumune.web.domain.post.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.rumune.web.domain.file.entity.File
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name ="product_post_file")
class ProductPostFile(
    @Id
    val id:Long,

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "productPost_id")
    val productPost: ProductPost,

    @ManyToOne
    @JoinColumn(name="file_uuid")
    val file: File,

    @Column(name="order_num")
    val order: Int,

    @Column(name="is_use")
    var isUse:Boolean = false
) {
}