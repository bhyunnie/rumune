package com.rumune.web.domain.post.api

import com.rumune.web.domain.post.application.ProductPostService
import com.rumune.web.domain.post.dto.ProductPostDto
import com.rumune.web.domain.post.dto.request.CreateProductPostRequest
import com.rumune.web.domain.post.dto.response.CreateProductPostResponse
import com.rumune.web.domain.post.dto.response.FindAllProductPostResponse
import com.rumune.web.domain.post.dto.response.FindProductPostResponse
import com.rumune.web.global.enum.Responses
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ProductPostApi(
    private val validateUtil: ValidateUtil,
    private val productPostService: ProductPostService
) {
    /**
     * 상품 게시글 작성
     */
    @PostMapping("/admin/api/v1/post/product")
    fun createProductPost(@ModelAttribute request:CreateProductPostRequest,hsr:HttpServletRequest):ResponseEntity<CreateProductPostResponse> {
        val userId = validateUtil.extractUserIdFromBearerToken(hsr)
        val (thumbnail,title,discount,deliveryFee,productList,content,domain,postImageURLList) = request
        val productPost = productPostService.createProductPost(
            thumbnail = thumbnail, title = title, discount= discount,
            deliveryFee = deliveryFee, productIdList = productList, content = content,
            userId = userId, domain = domain, postImageURLList = postImageURLList
        )
        return ResponseEntity.ok().body(
            CreateProductPostResponse(
                message = "상품 게시글 작성 완료",
                status = Responses.OK,
                result = ProductPostDto.from(productPost)
            )
        )
    }
    /**
     * 전체 게시글 조회
     * TODO: 페이지네이션 필요
     */
    @GetMapping("/api/v1/post/product")
    fun findAllPost(hsr: HttpServletRequest):ResponseEntity<FindAllProductPostResponse> {
        val productPostList = productPostService.findAll()
        return ResponseEntity.ok().body(
            FindAllProductPostResponse(
                message = "모든 게시글 조회 완료",
                status = Responses.OK,
                result = productPostList.map{ProductPostDto.from(it)}
            )
        )
    }
    /**
     * 상품 게시글 조회 (단건)
     */
    @GetMapping("/api/v1/post/product/{uuid}")
    fun findPost(hsr:HttpServletRequest, @PathVariable uuid:String):ResponseEntity<FindProductPostResponse>{
    val productPost = productPostService.findPostByUUID(UUID.fromString(uuid))
        return ResponseEntity.ok().body(
            FindProductPostResponse(
                message = "게시글 조회 성공",
                status = Responses.OK,
                result = ProductPostDto.from(productPost)
            )
        )
    }
}