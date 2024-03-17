package com.rumune.web.domain.product.application

import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.file.application.FileService
import com.rumune.web.domain.file.application.ProductFileService
import com.rumune.web.domain.product.dto.request.CreateProductRequest
import com.rumune.web.domain.product.entity.Product
import com.rumune.web.domain.product.entity.ProductImage
import com.rumune.web.domain.product.repository.ProductRepository
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productFileService: ProductFileService,
    private val fileService: FileService,
    private val validateUtil: ValidateUtil
) {

    // Create
    fun createProduct(name:String, price:Int, quantityLimit:Int, categoryId:Long): Product {
        try {
            return productRepository.save(
                Product(
                    name = name,
                    price = price,
                    quantityLimit = quantityLimit,
                    categories = listOf(Category(categoryId)).toMutableSet()
                )
            )
        } catch(e:Exception) {
            throw Exception("Error Occur When Create Product")
        }
    }

    /**
     * 상품 등록 및 이미지 파일 연결
     * @desc 상품 등록 및 이미지 파일들 연결
     */
    fun registProduct(request: CreateProductRequest, hsr:HttpServletRequest):Product {
        try {
            val fileList = mutableListOf<ProductImage>()
            val userId = validateUtil.extractUserIdFromBearerToken(hsr)
            val product = createProduct(request.name,request.price,request.quantityLimit, request.categoryId)

            for(i in request.files.indices) {
                val file = request.files[i]
                val uploadedFile = fileService.createFile(file, userId, "/product")
                fileList.add(ProductImage(
                    product = product,
                    image = uploadedFile,
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

    // READ
    fun findAllProduct():List<Product> {
        try {
            return productRepository.findAll()
        } catch (e: Exception) {
            throw Exception("상품 api 관련 에러")
        }
    }
}