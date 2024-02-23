package com.rumune.web.domain.user.application

import com.rumune.web.domain.user.entity.UserCountHistory
import com.rumune.web.domain.user.repository.UserCountHistoryRepository
import com.rumune.web.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserHistoryService(
    val userCountHistoryRepository: UserCountHistoryRepository,
    val userRepository: UserRepository
) {
    fun saveUserCountHistory() {
        userCountHistoryRepository.save(UserCountHistory(
            count = userRepository.count().toInt()
        ))
    }
}