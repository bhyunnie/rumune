package com.rumune.web.domain.user.application

import com.rumune.web.domain.user.entity.UserCountHistory
import com.rumune.web.domain.user.repository.UserCountHistoryRepository
import com.rumune.web.domain.user.repository.UserRepository
import com.rumune.web.global.util.DateUtil
import org.springframework.stereotype.Service

@Service
class UserHistoryService(
    val userCountHistoryRepository: UserCountHistoryRepository,
    val userRepository: UserRepository,
    val dateUtil: DateUtil,
) {
    fun saveUserCountHistory() {
        userCountHistoryRepository.save(
            UserCountHistory(
                count = userRepository.count().toInt(),
            ),
        )
    }

    fun getUserCountHistory(date: String): List<UserCountHistory> {
        val startTime = dateUtil.YYYYMMDDToOffsetDateTime(date)
        return userCountHistoryRepository.findUserCountHistoryBySnapShotTimeIsGreaterThanEqual(startTime)
    }
}
