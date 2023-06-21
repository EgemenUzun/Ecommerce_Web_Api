package com.example.demo.controller;

import com.example.demo.RabbitMQ.QueueSender;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final QueueSender queueSender;

    public ProductController(ProductService productService, QueueSender queueSender) {
        this.productService = productService;
        this.queueSender = queueSender;
    }
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        queueSender.send("Product Created. Product id is:"+createdProduct.getId());
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}