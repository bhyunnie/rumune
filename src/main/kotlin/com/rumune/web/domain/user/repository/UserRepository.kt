package com.rumune.web.domain.user.repository

import com.rumune.web.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

interface UserRepository: JpaRepository<User,Long> {
    fun findByUserId(userId:Long):Optional<User>
    fun findByEmail(email:String):Optional<User>
    fun findByProviderId(providerId:String):Optional<User>
}