package com.example.queryhandler;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.OrderDetails;
import com.example.queries.OrderDetailsQuery;
import com.example.repositories.OrderDetailsRepository;

@Component
public class OrderDetailsQueryService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepo;
    
    @QueryHandler
    public OrderDetails handleOrderDetailsQuery(OrderDetailsQuery query) {
        return this.orderDetailsRepo.findById(query.orderId).get();
    }
    
}
