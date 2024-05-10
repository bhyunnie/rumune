package com.rumune.web.global.util

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class DateUtil {
    fun YYYYMMDDToOffsetDateTime(yyyymmdd: String): OffsetDateTime {
        val date = LocalDate.parse(yyyymmdd, DateTimeFormatter.ofPattern("yyyyMMdd"))
        val dateTime = date.atStartOfDay()
        val zoneId = ZoneId.systemDefault()
        val zoneOffset = zoneId.rules.getOffset(dateTime)
        return dateTime.atOffset(zoneOffset)
    }

    fun offsetDateTimeToYYYYMMDD(dateTime: OffsetDateTime): String {
        val zoneId = ZoneId.of("Asia/Seoul")
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return dateTime.atZoneSameInstant(zoneId).format(formatter)
    }

    fun offsetDateTimeToYYYYMMDDHH(dateTime: OffsetDateTime): String {
        val zoneId = ZoneId.of("Asia/Seoul")
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH")
        return dateTime.atZoneSameInstant(zoneId).format(formatter)
    }

    fun offsetDateTimeToYYYYMMDDHHMM(dateTime: OffsetDateTime): String {
        val zoneId = ZoneId.of("Asia/Seoul")
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")
        return dateTime.atZoneSameInstant(zoneId).format(formatter)
    }

    fun offsetDateTimeToYYYYMMDDHHMMSS(dateTime: OffsetDateTime): String {
        val zoneId = ZoneId.of("Asia/Seoul")
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        return dateTime.atZoneSameInstant(zoneId).format(formatter)
    }
}
