package com.rumune.web.domain.product.application

import com.amazonaws.services.kms.model.NotFoundException
import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.file.application.FileService
import com.rumune.web.domain.file.application.ProductFileService
import com.rumune.web.domain.product.dto.request.CreateProductRequest
import com.rumune.web.domain.product.entity.Product
import com.rumune.web.domain.product.entity.ProductImage
import com.rumune.web.domain.product.repository.ProductRepository
import com.rumune.web.global.util.JwtUtil
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productFileService: ProductFileService,
    private val fileService: FileService,
    private val jwtUtil: JwtUtil
) {
    /**
     * 상품 등록 (단건)
     */
    fun registProduct(request: CreateProductRequest, userId:Long):Product {
        try {
            val fileList = mutableListOf<ProductImage>()
            val product = createProduct(request.name,request.price,request.quantityLimit, request.categoryId)
            for(i in request.files.indices) {
                val file = request.files[i]
                val uploadedFile = fileService.createFile(file, userId, "/product")
                fileList.add(ProductImage(
                    product = product,
                    file = uploadedFile,
                    order = i
                ))
                productFileService.createProductFile(product, uploadedFile, i)
            }
            product.image = fileList.toMutableSet()
            return product
        } catch (e: Exception) {
            throw Exception("상품 등록 에러")
        }
    }
    /**
     * 전체 상품 조회 (다건)
     * TODO: 페이지네이션 필요
     */
    fun findAllProduct():List<Product> {
        val productList = productRepository.findAll()
        if(productList.isEmpty()) throw NotFoundException("상품이 존재하지 않습니다.")
        return productList
    }
    /**
     * 상품 조회 (단건)
     */
    fun findProduct(productId:Long):Product {
        val productOptional = productRepository.findById(productId)
        if (productOptional.isEmpty) throw NotFoundException("상품이 존재하지 않습니다.")
        return productOptional.get()
    }
    /**
     * 상품 조회 (다건)
     * TODO: 페이지네이션 필요
     */
    fun findProductList(productIdList:List<Long>): List<Product> {
        val productList = productRepository.findProductList(productIdList)
        if (productList.isEmpty()) throw NotFoundException("상품이 존재하지 않습니다.")
        return productList
    }
    /**
     * 상품 등록
     */
    private fun createProduct(name:String, price:Int, quantityLimit:Int, categoryId:Long): Product {
        return productRepository.save(
            Product(
                name = name,
                price = price,
                quantityLimit = quantityLimit,
                categories = listOf(Category(categoryId)).toMutableSet()
            )
        )
    }
}