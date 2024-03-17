package com.rumune.web.domain.product.api

import com.rumune.web.global.enum.Responses
import com.rumune.web.domain.file.application.FileService
import com.rumune.web.domain.file.application.ProductFileService
import com.rumune.web.domain.product.application.ProductService
import com.rumune.web.domain.product.dto.request.CreateProductRequest
import com.rumune.web.domain.product.dto.response.FindProductResponse
import com.rumune.web.domain.product.dto.response.ProductResponse
import com.rumune.web.domain.product.entity.ProductImage
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ProductApi(
    private val productService: ProductService,
) {
    // Create
    @PostMapping("/admin/api/v1/product")
    fun createProduct(@ModelAttribute request: CreateProductRequest, hsr:HttpServletRequest): ResponseEntity<ProductResponse> {
        val product = productService.registProduct(request,hsr)
        return ResponseEntity.ok(
            ProductResponse(
                message = "상품 등록 완료",
                status = Responses.OK,
                result = listOf(
                    FindProductResponse.from(product)
                )
            )
        )
    }

    // Read
    @GetMapping("/admin/api/v1/product/all")
    fun findAllProduct():ResponseEntity<ProductResponse> {
        val result = productService.findAllProduct()
        return ResponseEntity.ok(
            ProductResponse(
                message = "전체 상품 조회 성공",
                status = Responses.OK,
                result = result.map{ FindProductResponse.from(it) }
            )
        )
    }
    // Update
    // Delete
}