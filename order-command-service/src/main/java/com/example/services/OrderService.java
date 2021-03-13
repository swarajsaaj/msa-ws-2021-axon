package com.example.services;

import java.util.concurrent.CompletableFuture;

import com.example.dto.OrderCreateDTO;

public interface OrderService {

    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO);

}
