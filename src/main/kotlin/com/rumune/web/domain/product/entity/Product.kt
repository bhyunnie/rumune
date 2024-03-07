package com.rumune.web.domain.product.entity

import com.rumune.web.domain.category.entity.Category
import com.rumune.web.domain.common.dto.BaseEntity
import com.rumune.web.domain.file.entity.File
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,

    @Column(name = "name")
    val name:String,

    @Column(name = "price")
    val price:Int,

    @Column(name = "quantityLimit")
    val quantityLimit: Int,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_image",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "file_id")]
    )
    val productImage: MutableSet<File> = HashSet(),

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "product_category",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    val categories: MutableSet<Category> = HashSet()
):BaseEntity<Long>() {
    override fun getId(): Long? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.new
    }
}