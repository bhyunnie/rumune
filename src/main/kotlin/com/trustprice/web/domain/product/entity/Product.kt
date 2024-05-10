package com.trustprice.web.domain.product.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.trustprice.web.domain.category.entity.Category
import com.trustprice.web.domain.common.dto.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "name", unique = true)
    val name: String = "",
    val price: Int = 0,
    val quantityLimit: Int = 0,
    val stock: Int = 0,
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    var image: MutableSet<ProductImage> = mutableSetOf(),
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinTable(
        name = "product_category",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")],
    )
    val categories: MutableSet<Category> = HashSet(),
) : BaseEntity<Long>() {
    override fun getId(): Long? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.new
    }
}
