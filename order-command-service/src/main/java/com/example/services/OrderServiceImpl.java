package com.example.services;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import com.example.aggregates.OrderStatus;
import com.example.commands.CreateOrderCommand;
import com.example.dto.OrderCreateDTO;

@Service
public class OrderServiceImpl implements OrderService {

    private final CommandGateway commandGateway;

    public OrderServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {
        return commandGateway.send(new CreateOrderCommand(orderCreateDTO.getId(),
                orderCreateDTO.getPrice(), orderCreateDTO.getCurrency(), String.valueOf(OrderStatus.CREATED)));
    }
}
