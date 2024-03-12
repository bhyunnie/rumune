package com.rumune.web.domain.product.api

import com.rumune.web.domain.common.enum.Responses
import com.rumune.web.domain.file.application.FileService
import com.rumune.web.domain.file.application.ProductFileService
import com.rumune.web.domain.product.application.ProductService
import com.rumune.web.domain.product.dto.request.CreateProductRequest
import com.rumune.web.domain.product.dto.response.ProductResponse
import com.rumune.web.domain.product.entity.ProductImage
import com.rumune.web.global.util.ValidateUtil
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class ProductApi(
    private val productService: ProductService,
    private val productFileService: ProductFileService,
    private val fileService: FileService,
    private val validateUtil: ValidateUtil
) {
    // Create
    @PostMapping("/admin/api/v1/product")
    fun createProduct(@ModelAttribute request: CreateProductRequest, hsr:HttpServletRequest): ResponseEntity<ProductResponse> {
        val fileList = mutableListOf<ProductImage>()
        val userId = validateUtil.extractUserIdFromBearerToken(hsr)
        val product = productService.createProduct(request.name,request.price,request.quantityLimit, request.categoryId)
        request.files.mapIndexed{index,file ->
            val fileUUID = UUID.randomUUID()
            val fileSize = file.size
            val fileURL = fileService.uploadToS3(file,fileUUID, "/product", userId)
            val file = fileService.createFile(fileUUID,userId,fileSize,fileURL)
            fileList.add(ProductImage(
                product = product,
                image = file,
                order = index
            ))

            productFileService.createProductFile(fileList,product, file, index)
        }

        product.image = fileList.toMutableSet()

        return ResponseEntity.ok(
            ProductResponse(
                message = "상품 등록 완료",
                status = Responses.OK,
                result = listOf(product)
            )
        )
    }

    // Read
    @GetMapping("/admin/api/v1/product/all")
    fun findAllProduct():ResponseEntity<ProductResponse> {
        val result = productService.findAllProduct()
        return ResponseEntity.ok(
            ProductResponse(
                message = "전체 상품 조회 성공",
                status = Responses.OK,
                result = result
            )
        )
    }
    // Update
    // Delete
}