package com.example.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class CreateOrderCommand {

    @TargetAggregateIdentifier
    public final String orderId;

    public final BigDecimal price;

    public final String currency;

    public final String orderStatus;

    public CreateOrderCommand(String orderId, BigDecimal price, String currency, String orderStatus) {
        this.orderId = orderId;
        this.price = price;
        this.currency = currency;
        this.orderStatus = orderStatus;
    }
}
