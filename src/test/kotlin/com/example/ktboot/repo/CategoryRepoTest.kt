package com.example.ktboot.repo

import com.example.ktboot.CategoryTestData
import com.example.ktboot.ProductTestData
import com.example.ktboot.config.AppConfig
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

private val logger = KotlinLogging.logger {}

@DataJpaTest
@Import(AppConfig::class)
@ActiveProfiles("test")
class CategoryRepoTest {
    @Autowired private lateinit var entityManager: TestEntityManager
    @Autowired private lateinit var categoryRepo: CategoryRepo
    @Autowired private lateinit var productRepo: ProductRepo

    @Test
    fun findByCategoryIdTest() {
        val category = CategoryTestData.category(null)
        val product = ProductTestData.product("foo")
        category.addProduct(product)
        entityManager.persist(category)

        val rs = categoryRepo.findByCategoryId(category.categoryId!!)!!
        assertThat(rs.categoryId).isEqualTo(category.categoryId!!)
        assertThat(rs.products.size).isEqualTo(1)
    }
}
