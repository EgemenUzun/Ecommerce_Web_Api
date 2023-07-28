package com.example.demo.controller;

import com.example.demo.dto.Purchase;
import com.example.demo.dto.PurchaseResponse;
import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CheckoutControllerTest {

    @Mock
    CheckoutService checkoutService;

    @InjectMocks
    CheckoutController checkoutController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder() {
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(new OrderItem(1L,"imgUrl",new BigDecimal(10),1,1L));
        Address address = new Address(1L,"street","city","state","country","zipcode");
        Set<Order> orders = new HashSet<>();
        orders.add(new Order(1L,"testnumber",1,new BigDecimal(10),"on the way",orderItems,address,address));
        Customer customer = new Customer(1L,"firstName","lastName","email",orders);
        Purchase purchase =new Purchase(customer,address,address,orders.stream().findFirst().get(),orderItems);

        when(checkoutService.placeOrder(any())).thenReturn(new PurchaseResponse("testnumber"));

        var response = checkoutController.placeOrder(purchase);

        assertNotNull(purchase);
        assertEquals("testnumber",response.getOrderTrackingNumber());

    }
}