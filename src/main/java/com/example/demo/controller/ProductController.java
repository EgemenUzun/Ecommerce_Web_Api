package com.example.demo.controller;

import com.example.demo.RabbitMQ.QueueSender;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@EnableScheduling
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final QueueSender queueSender;

    private  final CustomerRepository customerRepository;
    public ProductController(ProductService productService, QueueSender queueSender, CustomerRepository customerRepository) {
        this.productService = productService;
        this.queueSender = queueSender;
        this.customerRepository = customerRepository;
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
    Product randomProduct;
    @GetMapping("/discount")
    public Product discountProduct()
    {
        return randomProduct;
    }

    @Scheduled(fixedRate = 120000)//120000
    public Product Discount()
    {
        List<Product> products = productService.getAllProducts();
        Random random = new Random();
        int randomIndex = random.nextInt(products.size());
        randomProduct = products.get(randomIndex);
        double value = randomProduct.getUnitPrice().doubleValue();
        value -= value* 0.3;
        BigDecimal result = BigDecimal.valueOf(value);
        randomProduct.setUnitPrice(result);
        System.out.println(randomProduct.getId()+"/"+randomProduct.getName());
        queueSender.send("New discount arrived for "+getRandomCustomer().getUserName());
        return randomProduct;
    }
    Customer getRandomCustomer(){
        List<Customer> customers = customerRepository.findAll();
        Random random = new Random();
        int randomIndex = random.nextInt(customers.size());
        Customer randomCustomer = customers.get(randomIndex);
        return  randomCustomer;
    }

}