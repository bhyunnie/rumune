package com.trustprice.web.domain.category.dto

import com.trustprice.web.domain.category.entity.Category

class CategoryDto(
    val id: Long,
    val name: String,
) {
    companion object {
        fun from(c: Category): CategoryDto {
            return CategoryDto(
                id = c.id,
                name = c.name,
            )
        }
    }
}
