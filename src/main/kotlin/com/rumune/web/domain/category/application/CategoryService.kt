package com.rumune.web.domain.category.application

import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.category.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    /**
     * 카테고리 생성 (단건)
     */
    fun createCategory(categoryName:String):Category {
        val category = categoryRepository.save(Category(name=categoryName))
        return category
    }

    /**
     * 카테고리 조회 (다건)
     */
    fun findAllCategory():List<Category> {
        val categoryList = categoryRepository.findAll()
        if(categoryList.isEmpty()) throw com.amazonaws.services.kms.model.NotFoundException("카테고리를 찾을 수 없습니다.")
        return categoryList
    }
}