package com.rumune.web.domain.post.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.rumune.web.domain.common.dto.BaseEntity
import com.rumune.web.domain.file.entity.File
import com.rumune.web.domain.user.entity.User
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name="product_post")
class ProductPost(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid:UUID = UUID.randomUUID(),

    val title: String,
    val content: String,
    val discount: Double,
    val deliveryFee: Int,
    @OneToOne
    @JoinColumn(name="created_by")
    val createdBy: User,

    @OneToOne
    @JoinColumn(name="thumbnail_url", referencedColumnName = "file_url")
    val thumbnailURL: File,

    @OneToMany(mappedBy = "productPost")
    @JsonManagedReference
    val image: List<ProductPostFile>,

    @OneToMany(mappedBy = "productPost")
    @JsonManagedReference
    val products: List<ProductPostProduct>,
):BaseEntity<UUID>() {
    override fun getId(): UUID? {
        return this.uuid
    }

    override fun isNew(): Boolean {
        return this.new
    }
}