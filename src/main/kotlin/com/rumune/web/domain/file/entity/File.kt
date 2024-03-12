package com.rumune.web.domain.file.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.rumune.web.domain.common.dto.BaseEntity
import com.rumune.web.domain.product.entity.ProductImage
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "files")
class File(
    @Id
    @Column(name="file_uuid")
    val fileUUID: UUID = UUID.randomUUID(),

    @Column(name="upload_user_id")
    val uploadUserId: Long = 0,

    @Column(name="file_size")
    val fileSize: Long = 0,

    @Column(name="file_url")
    val fileURL: String = "",

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], mappedBy = "image")
    @JsonManagedReference
    val product: MutableSet<ProductImage> = mutableSetOf(),
):BaseEntity<UUID>() {
    override fun getId(): UUID? {
        return this.fileUUID
    }
    override fun isNew(): Boolean {
        return this.new
    }
}