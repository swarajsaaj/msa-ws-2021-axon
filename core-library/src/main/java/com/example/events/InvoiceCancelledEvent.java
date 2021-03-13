package com.example.events;

public class InvoiceCancelledEvent {

    public final String paymentId;

    public final String orderId;

    public InvoiceCancelledEvent(String paymentId, String orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }
}
