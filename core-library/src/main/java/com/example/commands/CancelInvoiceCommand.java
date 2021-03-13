package com.example.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CancelInvoiceCommand {

    @TargetAggregateIdentifier
    public final String paymentId;
    
    public final String orderId;

    public CancelInvoiceCommand(String paymentId,String orderId) {
        this.orderId = orderId;
        this.paymentId = paymentId;
    }
}
