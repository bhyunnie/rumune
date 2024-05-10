package com.rumune.web.domain.cart.api

import com.rumune.web.domain.cart.application.CartService
import com.rumune.web.domain.cart.dto.CartDto
import com.rumune.web.domain.cart.dto.CartProductDto
import com.rumune.web.domain.cart.dto.request.AddProductToCartRequest
import com.rumune.web.domain.cart.dto.response.AddProductToCartResponse
import com.rumune.web.domain.cart.dto.response.FindCartResponse
import com.rumune.web.domain.product.application.ProductService
import com.rumune.web.domain.product.entity.Product
import com.rumune.web.global.enum.Responses
import com.rumune.web.global.extensionFunctions.getUserId
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "장바구니", description = "장바구니 API 입니다.")
@RestController
class CartApi(
    private val productService: ProductService,
    private val cartService: CartService,
) {
    @Operation(summary = "장바구니 상품 추가", description = "장바구니에 상품을 여러건 추가합니다")
    @PostMapping("/api/v1/cart")
    fun addProductToCart(
        @Parameter(
            schema = Schema(implementation = AddProductToCartRequest::class),
            name = "cart",
            description = "장바구니 조회 요청",
            required = true,
            `in` = ParameterIn.QUERY,
        )
        @RequestBody request: AddProductToCartRequest,
        hsr: HttpServletRequest,
    ): ResponseEntity<AddProductToCartResponse> {
        val cartProductList = cartService.addProductToCart(hsr.getUserId(), request.productList)
        return ResponseEntity.ok().body(
            AddProductToCartResponse(
                message = "상품 추가 완료",
                status = Responses.OK,
                result = cartProductList.map { CartProductDto.from(it) },
            ),
        )
    }

    // TODO : 로그인 이전 목록도 추가 필요
    @Operation(summary = "장바구니 조회", description = "유저의 장바구니를 조회합니다.")
    @GetMapping("/api/v1/cart")
    fun findUserCart(
        @RequestParam beforeLoginList: String?,
        hsr: HttpServletRequest,
    ): ResponseEntity<FindCartResponse> {
        var productListBeforeLogin = listOf<Product>()
        if (beforeLoginList != null) {
            productListBeforeLogin =
                productService.findProductList(productIdList = beforeLoginList.split(",").map { it.toLong() })
        }
        val productListByCart = cartService.findUserCart(hsr.getUserId())

        return ResponseEntity.ok().body(
            FindCartResponse(
                message = "카트 조회 완료",
                status = Responses.OK,
                result = CartDto.from(productListByCart),
            ),
        )
    }
}
