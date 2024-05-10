package com.rumune.web.domain.cart

import com.rumune.web.domain.cart.dto.response.FindCartResponse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartApiTest(
    @LocalServerPort val port: Int,
) {
    private val restTemplate = TestRestTemplate()

    @Test
    @DisplayName("카트를 조회합니다.")
    fun test_1() {
        val token =
            ".eyJzdWIiOiJxdWRndXM5NjAxQG5hdmVyLmNvbSIsImlzcyI6InJ1bXVuZSIsImlhdCI6MTcxMzM0NzUwNywiZXhwIjoxNzEzMzU0NzA3fQ.icJPYjrI-JgUX8tcqZGjiWWVlL2XE9FAeW4YDA0LmEja7Gar2UbJ6I3slEdUFI1pOM6Ki0XRqnNEF5o5IncoJQ"
        val headers = HttpHeaders()
        headers.set("Authorization", "Bearer $token")
        val response = restTemplate.getForEntity<FindCartResponse>("http://localhost:$port/api/v1/cart")
        assert(response.statusCode == HttpStatus.OK)
    }
}
