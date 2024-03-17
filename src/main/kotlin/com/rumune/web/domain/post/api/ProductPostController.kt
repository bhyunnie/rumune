package com.rumune.web.domain.post.api

import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductPostController(private val validateUtil: ValidateUtil) {
    @PostMapping("/admin/api/v1/post/product")
    fun createProductPost(hsr:HttpServletRequest):ResponseEntity<String> {
        val userId = validateUtil.extractUserIdFromBearerToken(hsr)
        return ResponseEntity.ok("포스팅 완료")
    }
}