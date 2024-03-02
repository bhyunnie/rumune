package com.rumune.web.domain.product.entity

import com.rumune.web.domain.common.dto.BaseEntity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "product")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val UUID: UUID,

    @Column(name = "name")
    val name:String,

    @Column(name = "price")
    val price:Int,

    @Column(name = "quantityLimit")
    val quantityLimit: Int,

    @Column(name = "productImage")
    val productImage: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_category",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "category_name")]
    )
    val categories: MutableSet<Category> = HashSet()
):BaseEntity<UUID>() {
    override fun getId(): UUID? {
        return this.UUID
    }

    override fun isNew(): Boolean {
        return this.new
    }
}