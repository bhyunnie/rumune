package com.rumune.web.domain.cart.api

import com.rumune.web.domain.cart.application.CartService
import com.rumune.web.domain.cart.dto.CartDto
import com.rumune.web.domain.cart.dto.request.AddProductToCartRequest
import com.rumune.web.domain.cart.dto.response.FindCartResponse
import com.rumune.web.domain.cart.entity.CartProduct
import com.rumune.web.domain.product.application.ProductService
import com.rumune.web.domain.product.entity.Product
import com.rumune.web.global.enum.Responses
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CartApi(
    private val validateUtil: ValidateUtil,
    private val productService: ProductService,
    private val cartService: CartService
) {
    // Create
    @PostMapping("/api/v1/cart")
    fun addProductToCart(@RequestBody request:AddProductToCartRequest ,hsr:HttpServletRequest):ResponseEntity<List<CartProduct>> {
        val userId = validateUtil.extractUserIdFromBearerToken(hsr)
        val result = cartService.addProductToCart(userId, request.productList)
        return ResponseEntity.ok().body(
            result
        )
    }

    //Read

    /**
     * list - 로그인 이전 장바구니 추가 목록
     */
    @GetMapping("/api/v1/cart")
    fun findUserCart (@RequestParam list:String?,hsr:HttpServletRequest): ResponseEntity<FindCartResponse>{
        val userId = validateUtil.extractUserIdFromBearerToken(hsr)
        var productListBeforeLogin = listOf<Product>()
        if(list != null) productListBeforeLogin = productService.findProductList(productIdList=list.split(",").map{it.toLong()})
        val productListByCart = cartService.findUserCart(userId)

        return ResponseEntity.ok().body(
            FindCartResponse(
                message = "카트 조회 완료",
                status = Responses.OK,
                result = CartDto.from(productListByCart[0])
            )
        )
    }

//    @PostMapping("/api/v1/cart")
//    fun findGuestCart (): ResponseEntity<String> {
//        return ResponseEntity.ok().body("guest cart")
//    }
}