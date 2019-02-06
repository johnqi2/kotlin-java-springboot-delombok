package com.example.ktboot.model

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.ToString
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.util.ProxyUtils
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.NamedAttributeNode
import javax.persistence.NamedEntityGraph
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Version
import javax.validation.constraints.NotBlank

@EntityListeners(AuditingEntityListener::class)
@Entity
@Table(name = "category")
class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var categoryId: Int? = null

    @NotBlank
    @Column(name = "name", nullable = false)
    var name: String? = null

    @CreatedDate
    @JsonFormat(shape=JsonFormat.Shape.STRING,
        pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
    @Column(name = "created_at", updatable = false,
        columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    var createdAt: LocalDateTime? = null

    @CreatedBy
    @Column(name = "created_by")
    var createdBy: String? = null

    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(shape=JsonFormat.Shape.STRING,
        pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
    var updatedAt: LocalDateTime ? = null

    @LastModifiedBy
    @Column(name = "updated_by")
    var updatedBy: String? = ""

    @Version
    @Column(name ="version", nullable = false, columnDefinition = "INT NOT NULL DEFAULT 0")
    var version: Int = 0

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL],
        mappedBy = "category", orphanRemoval = true)
    var products: MutableSet<Product> = mutableSetOf()

    fun addProduct(product: Product) {
        this.products.add(product)
        product.category = this
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) {
            return true
        }
        if (javaClass != ProxyUtils.getUserClass(other)) {
            return false
        }
        return categoryId == (other as Category).categoryId
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString(): String {
        return "Category(categoryId=$categoryId, name=$name, products=$products)"
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
