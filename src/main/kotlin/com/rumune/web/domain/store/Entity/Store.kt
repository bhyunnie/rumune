package com.rumune.web.domain.store.Entity

import com.rumune.web.domain.common.dto.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "store")
@SQLDelete(sql = "update store set is_deleted = true where store_id=?")
data class Store(
    @Id
    @Column(name="storeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var storeId: Long = 0,

    @Column(name="name", unique = true, nullable = false)
    var name:String,

    @Column(name="description" , nullable=true)
    var description:String?
):BaseEntity<Long>() {
    override fun getId(): Long {
        return this.storeId
    }
    override fun isNew(): Boolean {
        return this.new
    }
}