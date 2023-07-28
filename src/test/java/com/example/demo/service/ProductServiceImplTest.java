package com.example.demo.service;

import com.example.demo.dao.ProductRepository;
import com.example.demo.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl service;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void createPorduct_test(){
        //Arrange
        var product = new Product(1L,1L,"Language Book","Book","description",new BigDecimal(10),"url",true,5);
        when(productRepository.save(any())).thenReturn(product);

        //Act
        var response = service.createProduct(product);

        //Assert
        assertEquals(product,response);
    }
    @Test
    public void getProduct_success_test(){
        //Arrange
        var product = new Product(1L,1L,"Language Book","Book","description",new BigDecimal(10),"url",true,5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        //Act
        var response = service.getProduct(1L);

        //Arrange
        assertEquals(product,response);
    }

    @Test
    public void getProduct_faild_test(){
        //Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        //Act
        var response = service.getProduct(1L);

        //Arrange
        assertEquals(null,response);
    }
    @Test
    public void getAllProduct_test(){
        //Arrange
        var product = new Product(1L,1L,"Language Book","Book","description",new BigDecimal(10),"url",true,5);
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findAll()).thenReturn(products);

        //Act
        var response = service.getAllProducts();

        assertEquals(products,response);
    }

    @Test
    public void  updateProduct_success_test(){
        //Arrange
        var product = new Product(1L,1L,"Language Book","Book","description",new BigDecimal(10),"url",true,5);
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(any())).thenReturn(product);

        //Act
        var response=service.updateProduct(1L,product);

        //Assert
        assertEquals(product,response);

    }

    @Test
    public void  updateProduct_faild_test(){
        //Arrange
        var product = new Product(1L,1L,"Language Book","Book","description",new BigDecimal(10),"url",true,5);
        when(productRepository.existsById(1L)).thenReturn(false);

        //Act
        var response=service.updateProduct(1L,product);

        //Assert
        assertEquals(null,response);

    }
    @Test
    public void deleteProduct_test(){
        //Arrange
        Long productIdToDelete = 123L;

        service.deleteProduct(productIdToDelete);

        verify(productRepository).deleteById(productIdToDelete);
    }

}
