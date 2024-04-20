package com.rumune.web.domain.category.api

import com.rumune.web.domain.category.application.CategoryService
import com.rumune.web.domain.category.dto.CategoryDto
import com.rumune.web.domain.category.dto.response.CategoryResponse
import com.rumune.web.domain.category.dto.response.CreateCategoryResponse
import com.rumune.web.global.enum.Responses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryApi(
    private val categoryService: CategoryService,
) {
    /**
     * 카테고리 생성 (단건)
     */
    @PostMapping("/admin/api/v1/category")
    fun createCategory(@RequestParam categoryName:String):ResponseEntity<CreateCategoryResponse> {
        val category = categoryService.createCategory(categoryName)
        return ResponseEntity.ok(
            CreateCategoryResponse(
                message = "카테고리 추가 완료",
                status = Responses.OK,
                result = CategoryDto.from(category)
            )
        )
    }
    /**
     * 카테고리 조회 (다건)
     */
    @GetMapping("/api/v1/category")
    fun findAllCategory():ResponseEntity<CategoryResponse> {
        val categoryList = categoryService.findAllCategory()
        return ResponseEntity.ok(
            CategoryResponse(
                message = "카테고리 조회 완료",
                status = Responses.OK,
                result = categoryList.map{CategoryDto.from(it)}
            )
        )
    }
}