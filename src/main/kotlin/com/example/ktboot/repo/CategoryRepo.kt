package com.example.ktboot.repo

import com.example.ktboot.model.Category
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface CategoryRepo : JpaRepository<Category, Int> {

    @EntityGraph(attributePaths = ["products"], type = EntityGraphType.LOAD)
    fun findByCategoryId(categoryId: Int): Category?
}


