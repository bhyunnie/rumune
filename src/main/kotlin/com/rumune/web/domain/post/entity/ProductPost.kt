package com.rumune.web.domain.post.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.rumune.web.domain.common.dto.BaseEntity
import com.rumune.web.domain.file.entity.File
import com.rumune.web.domain.user.entity.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import java.util.UUID

@Entity
@Table(name="product_post")
class ProductPost(
    @Id
    val uuid:UUID = UUID.randomUUID(),
    val title: String,
    @Column(columnDefinition = "TEXT")
    val content: String,
    val discount: Double,
    val deliveryFee: Int,
    val isPosted: Boolean = false,
    @Column(name="thumbnail_url")
    val thumbnailURL: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by")
    val createdBy: User,

    @OneToMany(mappedBy = "productPost")
    @JsonManagedReference
    var image: List<ProductPostFile> = listOf(),

    @OneToMany(mappedBy = "productPost")
    @JsonManagedReference
    var products: List<ProductPostProduct> = listOf(),
):BaseEntity<UUID>() {
    override fun getId(): UUID? {
        return this.uuid
    }

    override fun isNew(): Boolean {
        return this.new
    }
}