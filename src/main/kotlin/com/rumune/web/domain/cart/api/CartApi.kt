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
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CartApi(
    private val productService: ProductService,
    private val cartService: CartService
) {
    /**
     * 카트에 상품 담기 (Insert, 다건)
     */
    @PostMapping("/api/v1/cart")
    fun addProductToCart(@RequestBody request:AddProductToCartRequest ,hsr:HttpServletRequest):ResponseEntity<AddProductToCartResponse> {
        val cartProductList = cartService.addProductToCart(hsr.getUserId(), request.productList)
        return ResponseEntity.ok().body(
            AddProductToCartResponse(
                message = "상품 추가 완료",
                status = Responses.OK,
                result = cartProductList.map{CartProductDto.from(it)}
            )
        )
    }

    /**
     * 카트 조회 (Select, 단건)
     * TODO : 로그인 이전 목록도 추가 필요
     */
    @GetMapping("/api/v1/cart")
    fun findUserCart (@RequestParam list:String?,hsr:HttpServletRequest): ResponseEntity<FindCartResponse>{
        var productListBeforeLogin = listOf<Product>()
        if(list != null) productListBeforeLogin = productService.findProductList(productIdList=list.split(",").map{it.toLong()})
        val productListByCart = cartService.findUserCart(hsr.getUserId())

        return ResponseEntity.ok().body(
            FindCartResponse(
                message = "카트 조회 완료",
                status = Responses.OK,
                result = CartDto.from(productListByCart)
            )
        )
    }
}