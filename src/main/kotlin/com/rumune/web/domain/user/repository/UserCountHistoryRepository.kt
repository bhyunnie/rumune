package com.rumune.web.domain.user.repository

import com.rumune.web.domain.user.entity.UserCountHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCountHistoryRepository:JpaRepository<UserCountHistory, Long> {
}