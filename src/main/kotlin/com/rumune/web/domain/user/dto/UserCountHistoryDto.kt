package com.rumune.web.domain.user.dto

import com.rumune.web.domain.user.entity.UserCountHistory
import com.rumune.web.global.util.DateUtil

class UserCountHistoryDto(
    val count: Int,
    val time: String,
) {
    companion object {
        private val dateUtil: DateUtil = DateUtil()

        fun from(history: UserCountHistory): UserCountHistoryDto {
            return UserCountHistoryDto(
                count = history.count,
                time = dateUtil.offsetDateTimeToYYYYMMDDHHMMSS(history.snapShotTime),
            )
        }
    }
}
