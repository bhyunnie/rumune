package com.trustprice.web.domain.file.entity

import com.trustprice.web.domain.common.dto.BaseEntity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "files")
class File(
    @Id
    @Column(name = "file_uuid")
    val fileUUID: UUID = UUID.randomUUID(),
    @Column(name = "upload_user_id")
    val uploadUserId: Long = 0,
    @Column(name = "file_size")
    val fileSize: Long = 0,
    @Column(name = "file_url")
    val fileURL: String = "",
) : BaseEntity<UUID>() {
    override fun getId(): UUID? {
        return this.fileUUID
    }

    override fun isNew(): Boolean {
        return this.new
    }
}
