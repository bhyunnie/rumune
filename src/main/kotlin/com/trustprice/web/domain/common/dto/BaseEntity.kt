package com.trustprice.web.domain.common.dto

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PostLoad
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.OffsetDateTime

@MappedSuperclass
@EntityListeners(value = [AuditingEntityListener::class])
abstract class BaseEntity<T>() : Persistable<T> {
    @Transient
    var new = true

    @PrePersist
    @PostLoad
    fun markNotNew() {
        this.new = false
    }

    @PreUpdate
    fun setUpdatedTime() {
        this.updatedAt = OffsetDateTime.now()
    }

    @Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: OffsetDateTime = OffsetDateTime.now()

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
}
