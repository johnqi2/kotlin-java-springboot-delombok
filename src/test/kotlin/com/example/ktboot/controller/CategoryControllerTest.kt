package com.example.ktboot.controller

import com.example.ktboot.CategoryTestData
import com.example.ktboot.repo.CategoryRepo
import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CategoryController::class)
class CategoryControllerTest {
    @MockBean private lateinit var categoryRepo: CategoryRepo
    @Autowired private lateinit var mvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper

    @Test
    fun createCategoryTest() {
        val category = CategoryTestData.category(null)
        val savedCategory = CategoryTestData.category(1)
        given(categoryRepo.save(category)).willReturn(savedCategory)

        assertThat(mvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(category))
            )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", "/categories/1"))
        )
    }
}