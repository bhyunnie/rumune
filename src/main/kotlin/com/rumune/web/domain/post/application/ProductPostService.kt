package com.rumune.web.domain.post.application

import com.rumune.web.domain.file.application.FileService
import com.rumune.web.domain.file.entity.File
import com.rumune.web.domain.post.entity.ProductPost
import com.rumune.web.domain.post.entity.ProductPostFile
import com.rumune.web.domain.post.entity.ProductPostProduct
import com.rumune.web.domain.post.repository.ProductPostFileRepository
import com.rumune.web.domain.post.repository.ProductPostProductRepository
import com.rumune.web.domain.post.repository.ProductPostRepository
import com.rumune.web.domain.product.entity.Product
import com.rumune.web.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ProductPostService(
    private val productPostRepository: ProductPostRepository,
    private val userRepository: UserRepository,
    private val fileService: FileService,
    private val productPostProductRepository: ProductPostProductRepository,
    private val productPostFileRepository: ProductPostFileRepository
) {
    fun createProductPost(
        thumbnail:MultipartFile, title:String, discount:Int, deliveryFee:Int,
        productIdList:List<Long>, content: String, userId:Long, domain:String,postImageURLList:List<String>):ProductPost {
        val postUUID = UUID.randomUUID()
        val user = userRepository.findByUserId(userId)
        val file = fileService.createFile(thumbnail,user[0].userId,"/product_post")
        val fileUUIDList = postImageURLList.map{productImageURL -> productImageURL.split("/").last().split(".").first()}

        val post = productPostRepository.save(
            ProductPost(
                uuid = postUUID,
                title = title,
                content = content,
                discount = discount.toDouble(),
                deliveryFee = deliveryFee,
                isPosted = true,
                createdBy = user[0],
                thumbnailURL = file.fileURL,
            )
        )

        val products = productIdList.map{productId ->
            productPostProductRepository.save(ProductPostProduct(product=Product(productId), productPost = post))
        }

        val files = fileUUIDList.mapIndexed{index,fileUUID ->
            productPostFileRepository.save(ProductPostFile(
                productPost = post,
                file = File(fileUUID = UUID.fromString(fileUUID)),
                order = index,
                isUse = true
            ))
        }

        post.products = products
        post.image = files

        return post
    }

    fun findAll(): List<ProductPost> {
        return productPostRepository.findAll()
    }

    fun findPostByUUID(id:UUID): ProductPost? {
        val post = productPostRepository.findById(id)
        return if(post.isPresent) post.get() else null
    }
}