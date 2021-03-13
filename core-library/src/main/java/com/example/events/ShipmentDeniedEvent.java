package com.example.events;

public class ShipmentDeniedEvent {

    public final String shippingId;
    
    public final String orderId;

    public final String paymentId;

    public ShipmentDeniedEvent(String shippingId,String orderId, String paymentId) {
        this.shippingId=shippingId;
        this.orderId = orderId;
        this.paymentId = paymentId;
    }
}
