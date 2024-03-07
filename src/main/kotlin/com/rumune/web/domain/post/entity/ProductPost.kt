package com.rumune.web.domain.post.entity

import com.rumune.web.domain.common.dto.BaseEntity
import com.rumune.web.domain.file.entity.File
import com.rumune.web.domain.product.entity.Product
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name="product_post")
class ProductPost(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val uuid:UUID = UUID.randomUUID(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="thumbnail_file_id")
    val thumbnail: File,

    @Column(name="content")
    val content: String,

    @Column(name="created_by")
    val createdBy: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name="product_post_product",
        joinColumns = [JoinColumn(name = "product_post_uuid")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    val products: MutableSet<Product>,
):BaseEntity<UUID>() {
    override fun getId(): UUID? {
        return this.uuid
    }

    override fun isNew(): Boolean {
        return this.new
    }
}