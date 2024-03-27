package com.rumune.web.domain.product.api

import com.rumune.web.global.enum.Responses
import com.rumune.web.domain.product.application.ProductService
import com.rumune.web.domain.product.dto.request.CreateProductRequest
import com.rumune.web.domain.product.dto.ProductDto
import com.rumune.web.domain.product.dto.response.ProductResponse
import com.rumune.web.global.enum.Scope
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
                    ProductDto.from(product)
                )
            )
        )
    }

    // Read
    @GetMapping("/api/v1/product/all")
    fun findAllProduct():ResponseEntity<ProductResponse> {
        val result = productService.findAllProduct()
        return ResponseEntity.ok(
            ProductResponse(
                message = "전체 상품 조회 성공",
                status = Responses.OK,
                result = result.map{ ProductDto.from(it) }
            )
        )
    }

    @GetMapping("/api/v1/product")
    fun findProduct(
        @RequestParam scope:Scope,
        @RequestParam(required = false, defaultValue = "") list:String?):ResponseEntity<ProductResponse?> {
        var productIdList:List<Long> = listOf()

        if(list == null && scope != Scope.ALL) return validateFailResponse
        else if (list != null) productIdList = list.split(",").map{it.toLong()}

        when(scope) {
            Scope.ALL -> return ResponseEntity.ok().body(null)
            Scope.LIST -> {
                val productList = productService.findProductList(productIdList)
                if (productList.isEmpty()) return notFoundResponse
                return ResponseEntity.ok().body(ProductResponse(
                    message = "상품 조회 완료",
                    status = Responses.OK,
                    result = productList.map{
                        ProductDto.from(it)
                    })
                )
            }
            Scope.SINGLE -> {
                val productList = productService.findProduct(productIdList[0])
                if (productList.isEmpty()) return notFoundResponse
                return ResponseEntity.ok().body(ProductResponse(
                    message = "상품 조회 완료",
                    status = Responses.OK,
                    result = productList.map{
                        ProductDto.from(it)
                    })
                )
            }
        }
    }

    // Update
    // Delete

    // Responses
    val notFoundResponse = ResponseEntity.ok().body(
        ProductResponse(
            message = "상품을 찾을 수 없습니다.",
            status = Responses.NOT_FOUND,
            result = listOf()
        )
    )

    val validateFailResponse = ResponseEntity.ok().body(
        ProductResponse(
            message = "입력 값이 잘못되었습니다.",
            status = Responses.ERROR,
            result = listOf()
        )
    )
}