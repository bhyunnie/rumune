package com.trustprice.web.domain.file.application

import com.trustprice.web.domain.file.entity.File
import com.trustprice.web.domain.file.repository.ProductFileRepository
import com.trustprice.web.domain.product.entity.Product
import com.trustprice.web.domain.product.entity.ProductImage
import org.springframework.stereotype.Service

@Service
class ProductFileService(
    private val productFileRepository: ProductFileRepository,
) {
    fun createProductFile(
        product: Product,
        file: File,
        order: Int,
    ): ProductImage {
        return productFileRepository.save(
            ProductImage(
                product = product,
                file = file,
                order = order,
            ),
        )
    }
}
