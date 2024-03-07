package com.rumune.web.domain.file.entity

import com.rumune.web.domain.common.dto.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "files")
class File(
    @Id
    @Column(name="file_uuid")
    val fileUUID: UUID = UUID.randomUUID(),

    @Column(name="ext")
    val ext: String,

    @Column(name="upload_user_id")
    val uploadUserId: Long,

    @Column(name="file_size")
    val fileSize: Long,

    @Column(name="bucket")
    val bucketName: String,
):BaseEntity<UUID>() {
    override fun getId(): UUID? {
        return this.fileUUID
    }
    override fun isNew(): Boolean {
        return this.new
    }
}