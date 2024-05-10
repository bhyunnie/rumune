package com.trustprice.web.domain.cart.application

import com.amazonaws.services.kms.model.NotFoundException
import com.trustprice.web.domain.cart.dto.request.AddProductToCartRequestProduct
import com.trustprice.web.domain.cart.entity.Cart
import com.trustprice.web.domain.cart.entity.CartProduct
import com.trustprice.web.domain.cart.repository.CartProductRepository
import com.trustprice.web.domain.cart.repository.CartRepository
import com.trustprice.web.domain.product.repository.ProductRepository
import com.trustprice.web.domain.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val cartProductRepository: CartProductRepository,
    private val userRepository: UserRepository,
) {
    /**
     * 카트에 상품 담기
     */
    fun addProductToCart(
        userId: Long,
        productList: List<AddProductToCartRequestProduct>,
    ): List<CartProduct> {
        val productMap = productList.associateWith { it.count }.mapKeys { it.key.id }.toMutableMap()
        val cartOptional = cartRepository.findByUserId(userId)
        val cartProductEntityList = mutableListOf<CartProduct>()
        // 카트가 DB에 없으면 만들고 저장해서 카트에 상품을 담는다.
        val cart: Cart =
            if (cartOptional.isEmpty) {
                cartRepository.save(
                    Cart(
                        user = userRepository.findById(userId).get(),
                    ),
                )
            } else {
                cartOptional.get()
            }
        // 기존 상품 목록이 없다면 그냥 추가하고 있다면 개수를 수정한다.
        if (cart.product.isNotEmpty()) {
            cart.product.forEach {
                it.count += productMap[it.product.id] ?: 0
                cartProductEntityList.add(it)
                productMap.remove(it.product.id)
            }
        }
        // 기존 상품 목록에 더하고 새로 추가해야 할 것들은 전부 추가
        productRepository.findProductList(productIdList = productMap.map { it.key }).forEach { p ->
            productMap[p.id]?.let {
                cartProductEntityList.add(CartProduct(cart = cart, product = p, count = it))
            }
        }
        val result = cartProductRepository.saveAll(cartProductEntityList)

        return result
    }

    /**
     * 카트 조회 (단건)
     */
    fun findUserCart(userId: Long): Cart {
        val cartOptional = cartRepository.findByUserId(userId)
        if (cartOptional.isEmpty) throw NotFoundException("카트를 찾을 수 없습니다.")
        return cartOptional.get()
    }
}
