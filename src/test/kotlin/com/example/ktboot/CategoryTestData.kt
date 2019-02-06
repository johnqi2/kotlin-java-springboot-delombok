package com.example.ktboot

import com.example.ktboot.model.Category

object CategoryTestData {

    fun category(categoryId: Int? = 1, name: String = "foo"): Category {
        val category = Category()
        category.categoryId = categoryId
        category.name = name
        return category;
    }
}
