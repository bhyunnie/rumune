package com.trustprice.web.domain.user.repository

import com.trustprice.web.domain.user.entity.UserCountHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface UserCountHistoryRepository : JpaRepository<UserCountHistory, Long> {
    fun findUserCountHistoryBySnapShotTimeIsGreaterThanEqual(snapShotTime: OffsetDateTime): List<UserCountHistory>
}
