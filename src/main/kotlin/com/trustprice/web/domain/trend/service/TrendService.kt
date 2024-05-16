package com.trustprice.web.domain.trend.service

import com.amazonaws.services.kms.model.NotFoundException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class TrendService(
    val redisTemplate: RedisTemplate<String, String>,
) {
    fun findAllTrend(): List<String> {
        val dataList = redisTemplate.opsForList().range("trend_post", 0, -1)
        if (dataList == null || dataList.isEmpty()) {
            throw NotFoundException("최근 트렌드를 찾을 수 없습니다")
        }
        return dataList
    }
}
