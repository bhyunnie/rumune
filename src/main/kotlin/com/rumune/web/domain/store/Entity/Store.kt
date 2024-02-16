package com.rumune.web.domain.store.Entity

import com.rumune.web.domain.common.dto.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "store")
data class Store(
    @Id
    @Column(name="storeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var storeId: Long = 0,

    @Column(name="name", unique = true, nullable = false)
    val name:String,

    @Column(name="description" , nullable=true)
    val description:String?
):BaseEntity<Long>() {
    override fun getId(): Long {
        return this.storeId
    }
    override fun isNew(): Boolean {
        return this.new
    }
}