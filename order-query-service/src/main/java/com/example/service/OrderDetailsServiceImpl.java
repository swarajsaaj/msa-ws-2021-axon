package com.example.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.OrderDetails;
import com.example.queries.OrderDetailsQuery;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);
   
    @Autowired
    private QueryGateway queryGateway;

    @Override
    public OrderDetails getOrderDetails(String orderId) {
        CompletableFuture<OrderDetails> detailsFuture = this.queryGateway.query(new OrderDetailsQuery(orderId),
            OrderDetails.class);
        try {
            return detailsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("Failed to get order details: "+e);
        }
        return null;
    }

}
