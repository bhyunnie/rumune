package com.rumune.web.domain.category.dto

import com.rumune.web.domain.category.entity.Category

class CategoryDto(
    val id:Long,
    val name:String
) {
    companion object {
        fun from(c:Category): CategoryDto {
            return CategoryDto(
                id=c.id,
                name=c.name
            )
        }
    }
}