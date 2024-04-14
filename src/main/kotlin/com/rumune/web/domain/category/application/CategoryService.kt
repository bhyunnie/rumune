package com.rumune.web.domain.category.application

import com.rumune.web.domain.category.dto.CategoryDto
import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.category.repository.CategoryRepository
import com.rumune.web.global.exception.CategoryException
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    /**
     * 카테고리 생성 (단건)
     */
    fun createCategory(categoryName:String):CategoryDto {
        try {
            val result = categoryRepository.save(Category(name=categoryName))
            return CategoryDto.from(result)
        } catch (e:Exception) {
            throw CategoryException("카테고리 생성 중 에러가 발생했습니다.")
        }
    }

    /**
     * 카테고리 조회 (다건)
     */
    fun findAllCategory():List<CategoryDto> {
        try {
            val categoryList = categoryRepository.findAll()
            return categoryList.map{ CategoryDto.from(it) }
        } catch (e:Exception) {
            throw CategoryException("카테고리 조회 중 에러가 발생했습니다.")
        }
    }
}