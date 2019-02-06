package com.example.ktboot.controller

import com.example.ktboot.model.Category
import com.example.ktboot.repo.CategoryRepo
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryRepo: CategoryRepo) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createCategory(@Valid @RequestBody category: Category): ResponseEntity<Category> {
        if (category.categoryId != null || !category.products.isEmpty()) {
            return ResponseEntity.badRequest().build()
        }
        val rs = categoryRepo.save(category)
        return ResponseEntity.created(URI("/categories/${rs.categoryId}")).body(rs)
    }

    @GetMapping("/{categoryId}", produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getCategory(
        @PathVariable("categoryId") categoryId: Int,
        @RequestParam("includeProducts") includeProducts: Boolean = false
    ): ResponseEntity<Category> {

        var category: Category?
        if (includeProducts) {
            category = categoryRepo.findByCategoryId(categoryId)
        } else {
            category = categoryRepo.findById(categoryId).orElse(null)
            // Don't let Jackson trigger query for products
            category?.products = mutableSetOf()
        }
        return category?.let { ResponseEntity.ok(it) } ?: ResponseEntity.notFound().build()
    }
}
