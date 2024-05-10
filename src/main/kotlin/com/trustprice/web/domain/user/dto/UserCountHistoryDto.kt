package com.trustprice.web.domain.user.dto

import com.trustprice.web.domain.user.entity.UserCountHistory
import com.trustprice.web.global.util.DateUtil

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
