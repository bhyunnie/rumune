package com.rumune.web.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority

@Entity
@Table(name = "authorities")
@IdClass(Authority::class)
data class Authority (
    @Id
    @Column(name="user_id")
    var userId: Long,

    @Id
    @Column(name="name")
    var name: String,
):GrantedAuthority {
    override fun getAuthority(): String {
        return this.name
    }
}

// baseEntity 를 상속하면 IdClass Authority 클래스가 통째로 들어가서 전부 ID가 되어버린다. 날짜를 수정할 때 그로인해 문제가 발생한다.