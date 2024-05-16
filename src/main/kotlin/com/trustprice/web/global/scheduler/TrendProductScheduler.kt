package com.trustprice.web.global.scheduler

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TrendProductScheduler(
    val redisTemplate: RedisTemplate<String, String>,
) {
    @Scheduled(cron = "0/30 * * * * *")
    fun makeTopTrendProductList() {
        println("마지막 조회 기록을 통한 추천 리스트 반영")
        val oldTrendListSize: Int =
            run {
                val trendList: List<String> = redisTemplate.opsForList().range("trend_post", 0, -1) ?: emptyList()
                trendList.size
            }

        val newTrendList =
            run {
                val storedList: List<String> = redisTemplate.opsForList().range("read_post", 0, -1) ?: emptyList()
                val sortedTrend = storedList.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
                sortedTrend.map { it.first }.slice(0..9.coerceAtMost(sortedTrend.size - 1))
            }

        println(newTrendList)

        redisTemplate.execute { connection ->
            connection.apply {
                multi()
                newTrendList.map {
                    listCommands().lPush("trend_list".toByteArray(), it.toByteArray())
                }
                if (oldTrendListSize + newTrendList.size > 10) {
                    for (i in 0..oldTrendListSize + newTrendList.size - 10) {
                        listCommands().rPop("trend_list".toByteArray())
                    }
                }
                exec()
            }
        }
    }
}
