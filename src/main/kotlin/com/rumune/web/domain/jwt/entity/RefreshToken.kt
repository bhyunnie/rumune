package com.rumune.web.domain.jwt.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="refreshtokens")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    val id:Long = 0,

    @Column(name = "user_id" , nullable = false, unique = true)
    val userId: Long,

    @Column(name="refresh_token", nullable = false)
    var refreshToken: String,

    @Column(name="email", nullable = false)
    val email:String,
) {
    fun update(newRefreshToken:String): RefreshToken {
        this.refreshToken = newRefreshToken
        return this
    }
}