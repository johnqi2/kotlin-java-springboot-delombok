package com.example.ktboot.controller;

import com.example.ktboot.ProductTestData;
import com.example.ktboot.repo.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
  @MockBean private ProductRepo productRepo;
  @Autowired private MockMvc mvc;
  @Autowired private ObjectMapper objectMapper;

  @SneakyThrows
  @Test
  public void getProductTest() {
    var productId = 100;
    var product = ProductTestData.product(productId, "foo", 1);

    given(productRepo.findById(productId)).willReturn(Optional.of(product));
    assertThat(mvc.perform(get("/products/" + productId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productId").value(100))
        .andExpect(jsonPath("$.name").value("foo"))
        .andExpect(jsonPath("$.categoryId").value(1)));
  }

  @SneakyThrows
  @Test
  public void createProductTest() {
    int categoryId = 10;
    String name = "foo";
    var product = ProductTestData.product(null, name, categoryId);
    var savedProductId = 1;
    var savedProduct = ProductTestData.product(savedProductId, name, categoryId);

    given(productRepo.save(product)).willReturn(savedProduct);
    assertThat(mvc.perform(
        post("/products")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/products/1")));
  }
}
