package com.rumune.web.domain.category.application

import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.category.repository.CategoryRepository
import com.rumune.web.global.exception.CategoryException
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun createCategory(categoryName:String):Boolean {
        try {
            categoryRepository.save(Category(name=categoryName))
            return true
        } catch (e:Exception) {
            throw CategoryException("카테고리 생성 중 에러가 발생했습니다.")
        }
    }

    fun findAllCategory():List<Category> {
        try {
            return categoryRepository.findAll()
        } catch (e:Exception) {
            throw CategoryException("카테고리 조회 중 에러가 발생했습니다.")
        }
    }
}