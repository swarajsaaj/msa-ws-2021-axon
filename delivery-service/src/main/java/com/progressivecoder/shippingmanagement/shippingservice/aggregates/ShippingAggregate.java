package com.progressivecoder.shippingmanagement.shippingservice.aggregates;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.commands.CreateShippingCommand;
import com.example.events.OrderShippedEvent;
import com.example.events.ShipmentDeniedEvent;

@Aggregate
public class ShippingAggregate {

    @AggregateIdentifier
    private String shippingId;

    private String orderId;

    private String paymentId;

    public ShippingAggregate() {
    }

    @CommandHandler
    public ShippingAggregate(CreateShippingCommand createShippingCommand){
        if(createShippingCommand.orderId.startsWith("bad-")) {
            System.out.println("Denied shipping for: "+createShippingCommand.orderId);
            AggregateLifecycle.apply(new ShipmentDeniedEvent(createShippingCommand.shippingId,createShippingCommand.orderId, createShippingCommand.paymentId));
        }else {
            System.out.println("Order shipped for: "+createShippingCommand.orderId);
            AggregateLifecycle.apply(new OrderShippedEvent(createShippingCommand.shippingId, createShippingCommand.orderId, createShippingCommand.paymentId));            
        }
    }
    
    @EventSourcingHandler
    protected void on(ShipmentDeniedEvent shipmentDeniedEvent){
        this.shippingId = shipmentDeniedEvent.shippingId;
        this.orderId = shipmentDeniedEvent.orderId;
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent orderShippedEvent){
        this.shippingId = orderShippedEvent.shippingId;
        this.orderId = orderShippedEvent.orderId;
    }
}
