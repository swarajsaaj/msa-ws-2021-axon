package com.example.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.commands.CancelInvoiceCommand;
import com.example.commands.CreateInvoiceCommand;
import com.example.events.InvoiceCancelledEvent;
import com.example.events.InvoiceCreatedEvent;

@Aggregate
public class InvoiceAggregate {

    @AggregateIdentifier
    private String paymentId;

    private String orderId;

    private InvoiceStatus invoiceStatus;

    public InvoiceAggregate() {
    }

    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand){
        System.out.println("Creating invoice for: "+createInvoiceCommand.orderId);
        AggregateLifecycle.apply(new InvoiceCreatedEvent(createInvoiceCommand.paymentId, createInvoiceCommand.orderId));
    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedEvent invoiceCreatedEvent){
        this.paymentId = invoiceCreatedEvent.paymentId;
        this.orderId = invoiceCreatedEvent.orderId;
        this.invoiceStatus = InvoiceStatus.PAID;
    }
    
    @CommandHandler
    public void handle(CancelInvoiceCommand cancelInvoiceCommand){
        System.out.println("Cancelling invoice for: "+cancelInvoiceCommand.orderId);
        AggregateLifecycle.apply(new InvoiceCancelledEvent(cancelInvoiceCommand.paymentId, cancelInvoiceCommand.orderId));
    }
    
    @EventSourcingHandler
    protected void on(InvoiceCancelledEvent invoiceCancelledEvent){
        this.paymentId = invoiceCancelledEvent.paymentId;
        this.orderId = invoiceCancelledEvent.orderId;
        this.invoiceStatus = InvoiceStatus.PAYMENT_REVERSED;
    }
    
}
