package com.rumune.web.domain.user.repository

import com.rumune.web.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    override fun findById(id: Long): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun findByProviderId(providerId: String): Optional<User>
}
