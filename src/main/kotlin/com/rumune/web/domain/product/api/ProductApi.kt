package com.rumune.web.domain.product.api

import com.rumune.web.global.enum.Responses
import com.rumune.web.domain.product.application.ProductService
import com.rumune.web.domain.product.dto.request.CreateProductRequest
import com.rumune.web.domain.product.dto.ProductDto
import com.rumune.web.domain.product.dto.response.ProductListResponse
import com.rumune.web.domain.product.dto.response.ProductResponse
import com.rumune.web.global.enum.Scope
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class ProductApi(
    private val productService: ProductService,
) {
    /**
     * 상품 등록 (단건)
     */
    @PostMapping("/admin/api/v1/product")
    fun createProduct(@ModelAttribute request: CreateProductRequest, hsr:HttpServletRequest): ResponseEntity<ProductResponse> {
        val product = productService.registProduct(request,hsr)
        return ResponseEntity.ok(
            ProductResponse(message = "상품 등록 완료", status = Responses.OK, result = ProductDto.from(product))
        )
    }
    /**
     * 전체 상품 조회 (다건)
     */
    @GetMapping("/api/v1/product/all")
    fun findAllProduct():ResponseEntity<ProductListResponse> {
        val result = productService.findAllProduct()
        return ResponseEntity.ok(
            ProductListResponse(
                message = "전체 상품 조회 성공",
                status = Responses.OK,
                result = result.map{ ProductDto.from(it) }
            )
        )
    }
    /**
     * 상품 조회 (다건)
     */
    @GetMapping("/api/v1/product/list")
    fun findProductList(@RequestParam(required = true) list:String):ResponseEntity<ProductListResponse> {
        val productIdList = list.split(",").map{it.toLong()}
        val productList = productService.findProductList(productIdList)
        return ResponseEntity.ok()
            .body(ProductListResponse("상품 조회 완료",Responses.OK,productList.map{ProductDto.from(it)}))
    }
    /**
     * 상품 조회 (단건)
     */
    @GetMapping("/api/v1/product/{id}")
    fun findProduct(
        @PathVariable id: String
    ):ResponseEntity<ProductResponse> {
        val product = productService.findProduct(id.toLong())
        return ResponseEntity.ok()
            .body(ProductResponse("상품 조회 완료",Responses.OK, ProductDto.from(product)))
    }
}