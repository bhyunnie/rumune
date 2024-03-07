package com.rumune.web.domain.product.application

import com.rumune.web.domain.product.entity.Product
import com.rumune.web.domain.product.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    fun findAllProduct():List<Product> {
        try {
            return productRepository.findAll()
        } catch (e: Exception) {
            throw Exception("상품 api 관련 에러")
        }
    }
}