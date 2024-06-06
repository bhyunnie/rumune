package com.trustprice.web.global.scheduler

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TrendProductScheduler(
    val redisTemplate: RedisTemplate<String, String>,
) {
    /**
     * 가장 많이 본 상품 리스트 추가 to Redis
     */
    @Scheduled(cron = "0 0/5 * * * *")
    fun makeTopTrendProductList() {
        val oldTrendListSize: Int =
            run {
                val trendList: List<String> = redisTemplate.opsForList().range("trend_post", 0, -1) ?: emptyList()
                trendList.size
            }
        val newTrendList =
            run {
                val storedList: List<String> = redisTemplate.opsForList().range("read_post", 0, -1) ?: emptyList()
                val sortedTrend = storedList.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
                sortedTrend.take(10.coerceAtMost(sortedTrend.size - 1)).map { it.first }
            }
        // 트랜잭션 형태로 수행
        redisTemplate.execute { connection ->
            connection.apply {
                multi()
                newTrendList.forEach {
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
