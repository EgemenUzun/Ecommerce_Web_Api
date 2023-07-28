package com.example.demo.controller;

import com.example.demo.RabbitMQ.QueueSender;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    QueueSender queueSender;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createProduct_test(){
        Product product = new Product(1L,1L,"Programing Book","Book","Des",new BigDecimal(10),"url",true,5);

        when(productService.createProduct(any())).thenReturn(product);

        var response = productController.createProduct(product);

        assertEquals(product,response.getBody());
        assertEquals(201,response.getStatusCode().value());
        verify(queueSender,times(1)).send("Product Created. Product id is:"+product.getId());

    }

    @Test
    public void getProduct_test(){
        Product product = new Product(1L,1L,"Programing Book","Book","Des",new BigDecimal(10),"url",true,5);

        when(productService.getProduct(anyLong())).thenReturn(product);

        var response =productController.getProduct(1L);

        assertEquals(product,response);
    }
    @Test
    public void testDiscount(){
        Product product = new Product(1L,1L,"Programing Book","Book","Des",new BigDecimal(10),"url",true,5);
        List<Product>products =new ArrayList<>();
        products.add(product);
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer(1L, "John", "ln","john@example.com","jhon",new HashSet<>());
        customers.add(customer);
        when(productService.getAllProducts()).thenReturn(products);
        when(customerRepository.findAll()).thenReturn(customers);

        var response = productController.Discount();

        BigDecimal expectedDiscountedPrice = BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(0.7));
        assertEquals(expectedDiscountedPrice, response.getUnitPrice());
        verify(queueSender).send("New discount arrived for "  +customer.getUserName());

    }
}