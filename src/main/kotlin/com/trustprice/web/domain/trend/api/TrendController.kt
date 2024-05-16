package com.trustprice.web.domain.trend.api

import com.trustprice.web.domain.trend.dto.response.FindAllTrendResponse
import com.trustprice.web.domain.trend.service.TrendService
import com.trustprice.web.global.enum.Responses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TrendController(
    val trendService: TrendService,
) {
    @GetMapping("/api/v1/trend/all")
    fun redisTest(): ResponseEntity<FindAllTrendResponse> {
        return ResponseEntity.ok().body(
            FindAllTrendResponse(
                status = Responses.OK,
                message = "최신 트렌드 조회 완료",
                result = trendService.findAllTrend(),
            ),
        )
    }
}
