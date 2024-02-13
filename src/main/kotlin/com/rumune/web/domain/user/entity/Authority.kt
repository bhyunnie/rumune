package com.rumune.web.domain.user.entity

import com.rumune.web.domain.common.dto.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "authorities")
@IdClass(Authority::class)
data class Authority (
    @Id
    @Column(name="user_id")
    val userId: Long,

    @Id
    @Column(name="name")
    val name: String,
):GrantedAuthority,BaseEntity<Authority>() {
    override fun getAuthority(): String {
        return this.name
    }

    override fun getId(): Authority {
        return Authority(this.userId,this.name)
    }

    override fun isNew(): Boolean {
        return super.new
    }
}