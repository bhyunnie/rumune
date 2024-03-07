package com.rumune.web.domain.product.api

import com.rumune.web.domain.common.enum.Responses
import com.rumune.web.domain.product.application.ProductService
import com.rumune.web.domain.product.dto.response.ProductResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductApi(
    private val productService: ProductService
) {
    // Create
    // Read
    @GetMapping("/admin/api/v1/product/all")
    fun findAllProduct():ResponseEntity<ProductResponse> {
        val result = productService.findAllProduct()
        return ResponseEntity.ok(
            ProductResponse(
                message = "전체 상품 조회 성공",
                status = Responses.OK,
                result = result
            )
        )
    }
    // Update
    // Delete
}