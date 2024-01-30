package com.rumune.web.domain.user.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UuidGenerator
import org.springframework.boot.context.properties.bind.DefaultValue
import java.util.UUID

@Entity
@Table(name="users")
class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id")
    val id: UUID? = null,

    @Column(name = "email", unique = true, nullable = false)
    val email: String,

    @Column(name = "provider", nullable = false)
    val provider: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "role", nullable = false)
    val role: String? = null
) {}