package com.example.events;

import java.math.BigDecimal;

public class OrderCreatedEvent {

    public final String orderId;

    public final BigDecimal price;

    public final String currency;

    public final String orderStatus;

    public OrderCreatedEvent(String orderId, BigDecimal price, String currency, String orderStatus) {
        this.orderId = orderId;
        this.price = price;
        this.currency = currency;
        this.orderStatus = orderStatus;
    }
}
