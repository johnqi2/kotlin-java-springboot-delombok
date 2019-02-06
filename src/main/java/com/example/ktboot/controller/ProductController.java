package com.example.ktboot.controller;

import com.example.ktboot.model.Product;
import com.example.ktboot.repo.ProductRepo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping(value = "/{productId}", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Product> getProduct(@PathVariable("productId") int productId) {
        return productRepo.findById(productId)
            .map(product -> ResponseEntity.ok(product))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
        produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        if (product.getProductId() != null || product.getCategoryId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Product rs = productRepo.save(product);
        return ResponseEntity.created(URI.create("/products/" + rs.getProductId())).body(rs);
    }
}
