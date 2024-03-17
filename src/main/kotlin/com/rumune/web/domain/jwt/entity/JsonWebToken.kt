package com.rumune.web.domain.jwt.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="json_web_token")
data class JsonWebToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    val id:Long = 0,

    @Column(name = "user_id" , nullable = false, unique = true)
    val userId: Long,

    @Column(name="jwt", nullable = false)
    var jwt: String,
) {
}