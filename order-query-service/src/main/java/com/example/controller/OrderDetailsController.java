package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.OrderDetails;
import com.example.service.OrderDetailsService;

@RestController
public class OrderDetailsController {
    
    @Autowired
    public OrderDetailsService service;
    
    @GetMapping("/order/{id}")
    public OrderDetails getOrderDetails(@PathVariable("id") String orderId) {
        return this.service.getOrderDetails(orderId);
    }
    
}
