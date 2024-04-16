package com.rumune.web.domain.post.application

import com.amazonaws.services.kms.model.NotFoundException
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
    /**
     * 상품 게시글 작성
     */
    fun createProductPost(
        thumbnail:MultipartFile, title:String, discount:Int, deliveryFee:Int,
        productIdList:List<Long>, content: String, userId:Long, domain:String,postImageURLList:List<String>):ProductPost {
        val postUUID = UUID.randomUUID()
        val user = userRepository.findById(userId)
        val file = fileService.createFile(thumbnail,user.get().id,"/product_post")
        val fileUUIDList = postImageURLList.map{productImageURL -> productImageURL.split("/").last().split(".").first()}
        val post = productPostRepository.save(
            ProductPost(
                uuid = postUUID,
                title = title,
                content = content,
                discount = discount.toDouble(),
                deliveryFee = deliveryFee,
                isPosted = true,
                createdBy = user.get(),
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

    /**
     * 전체 상품 게시글 조회 (다건)
     */
    fun findAll(): List<ProductPost> {
        val postList = productPostRepository.findAll()
        if(postList.isEmpty()) throw NotFoundException("게시글이 없습니다.")
        return postList
    }

    /**
     * 상품 게시글 조회 (단건)
     */
    fun findPostByUUID(id:UUID): ProductPost {
        val postOptional = productPostRepository.findById(id)
        if (postOptional.isEmpty) throw NotFoundException("게시글을 찾을 수 없습니다.")
        return postOptional.get()
    }
}