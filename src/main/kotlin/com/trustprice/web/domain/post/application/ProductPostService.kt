package com.trustprice.web.domain.post.application

import com.amazonaws.services.kms.model.NotFoundException
import com.trustprice.web.domain.file.application.FileService
import com.trustprice.web.domain.file.entity.File
import com.trustprice.web.domain.post.entity.ProductPost
import com.trustprice.web.domain.post.entity.ProductPostFile
import com.trustprice.web.domain.post.entity.ProductPostProduct
import com.trustprice.web.domain.post.repository.ProductPostFileRepository
import com.trustprice.web.domain.post.repository.ProductPostProductRepository
import com.trustprice.web.domain.post.repository.ProductPostRepository
import com.trustprice.web.domain.product.entity.Product
import com.trustprice.web.domain.user.repository.UserRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ProductPostService(
    private val productPostRepository: ProductPostRepository,
    private val userRepository: UserRepository,
    private val fileService: FileService,
    private val productPostProductRepository: ProductPostProductRepository,
    private val productPostFileRepository: ProductPostFileRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) {
    /**
     * 상품 게시글 작성
     */
    fun createProductPost(
        thumbnail: MultipartFile,
        title: String,
        discount: Int,
        deliveryFee: Int,
        productIdList: List<Long>,
        content: String,
        userId: Long,
        domain: String,
        postImageURLList: List<String>,
    ): ProductPost {
        val postUUID = UUID.randomUUID()
        val user = userRepository.findById(userId)
        val file = fileService.createFile(thumbnail, user.get().id, "/product_post")
        val fileUUIDList =
            postImageURLList.map { productImageURL -> productImageURL.split("/").last().split(".").first() }
        val post =
            productPostRepository.save(
                ProductPost(
                    uuid = postUUID,
                    title = title,
                    content = content,
                    discount = discount.toDouble(),
                    deliveryFee = deliveryFee,
                    isPosted = true,
                    createdBy = user.get(),
                    thumbnailURL = file.fileURL,
                ),
            )
        val products =
            productIdList.map { productId ->
                productPostProductRepository.save(ProductPostProduct(product = Product(productId), productPost = post))
            }
        val files =
            fileUUIDList.mapIndexed { index, fileUUID ->
                productPostFileRepository.save(
                    ProductPostFile(
                        productPost = post,
                        file = File(fileUUID = UUID.fromString(fileUUID)),
                        order = index,
                        isUse = true,
                    ),
                )
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
        if (postList.isEmpty()) throw NotFoundException("게시글이 없습니다.")
        return postList
    }

    /**
     * 상품 게시글 조회 By 카테고리 (다건)
     */
    fun findPostByCategory(category: String): List<ProductPost> {
        val productPostList = productPostRepository.findProductPostByCategoryName(category)
        if (productPostList.isEmpty()) throw NotFoundException("게시글을 찾을 수 없습니다.")
        return productPostList
    }

    /**
     * 상품 게시글 조회 (단건)
     */
    fun findPostByUUID(id: UUID): ProductPost {
        val post =
            productPostRepository.findById(id).orElseThrow {
                NotFoundException("게시글을 찾을 수 없습니다.")
            }
        savePostUUIDOnRedis(id)
        return post
    }

    /**
     * 리스트 길이 500을 유지하는 레디스의 게시글 조회목록
     */
    private fun savePostUUIDOnRedis(id: UUID) {
        val storedListSize = redisTemplate.opsForList().size("read_post") ?: 0
        // 데이터 정합성 보장을 위해서 트랜잭션 방식으로 수행
        redisTemplate.execute { connection ->
            connection.apply {
                multi()
                listCommands().lPush("read_post".toByteArray(), id.toString().toByteArray())
                if (storedListSize >= 500) {
                    listCommands().rPop("read_post".toByteArray())
                }
                exec()
            }
        }
    }
}
