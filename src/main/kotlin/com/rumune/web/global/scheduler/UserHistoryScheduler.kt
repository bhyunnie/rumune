package com.rumune.web.global.scheduler

import com.rumune.web.domain.user.application.UserHistoryService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class UserHistoryScheduler(
    private val userHistoryService: UserHistoryService,
) {
    @Scheduled(cron = "0 0 0 * * *")
    fun insertUserCountHistory() {
        userHistoryService.saveUserCountHistory()
    }
}
