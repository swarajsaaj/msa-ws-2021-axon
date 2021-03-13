package com.example.aggregates;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.commands.CreateOrderCommand;
import com.example.commands.UpdateOrderStatusCommand;
import com.example.events.OrderCreatedEvent;
import com.example.events.OrderUpdatedEvent;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;

    private BigDecimal price;

    private String currency;

    private OrderStatus orderStatus;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.orderId,
                createOrderCommand.price, createOrderCommand.currency, createOrderCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.orderId;
        this.price = orderCreatedEvent.price;
        this.currency = orderCreatedEvent.currency;
        this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
    }

    @CommandHandler
    protected void on(UpdateOrderStatusCommand updateOrderStatusCommand){
        AggregateLifecycle.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId, updateOrderStatusCommand.orderStatus));
    }

    @EventSourcingHandler
    protected void on(OrderUpdatedEvent orderUpdatedEvent){
        this.orderId = orderUpdatedEvent.orderId;
        this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
    }
}
