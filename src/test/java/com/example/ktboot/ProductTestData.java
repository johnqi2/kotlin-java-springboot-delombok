package com.example.ktboot;

import com.example.ktboot.model.Product;

public class ProductTestData {
  public static Product product(Integer productId, String name, Integer categoryId) {
    Product product = new Product();
    product.setProductId(productId);
    product.setName(name);
    product.setCategoryId(categoryId);
    return product;
  }

  public static Product product(String name) {
    return product(null, name, null);
  }
}
