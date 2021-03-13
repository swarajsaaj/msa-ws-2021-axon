package com.example.projection;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.OrderDetails;
import com.example.events.OrderCreatedEvent;
import com.example.events.OrderUpdatedEvent;
import com.example.repositories.OrderDetailsRepository;

@Component
public class OrderProjection {

    @Autowired
    private OrderDetailsRepository orderDetailsRepo;

    @EventHandler
    public void handle(OrderCreatedEvent evt) {
        OrderDetails orderDet = new OrderDetails();
        orderDet.setId(evt.orderId);
        orderDet.setCost(evt.price.doubleValue());
        orderDet.setStatus(evt.orderStatus);

        this.orderDetailsRepo.save(orderDet);
    }

    @EventHandler
    public void handle(OrderUpdatedEvent evt) {
        OrderDetails orderDetails = this.orderDetailsRepo.findById(evt.orderId).get();

        orderDetails.setStatus(evt.orderStatus);
        this.orderDetailsRepo.save(orderDetails);
    }

}
