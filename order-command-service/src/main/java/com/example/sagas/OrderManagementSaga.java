package com.example.sagas;

import java.util.UUID;

import javax.inject.Inject;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import com.example.aggregates.OrderStatus;
import com.example.commands.CancelInvoiceCommand;
import com.example.commands.CreateInvoiceCommand;
import com.example.commands.CreateShippingCommand;
import com.example.commands.UpdateOrderStatusCommand;
import com.example.events.InvoiceCreatedEvent;
import com.example.events.OrderCreatedEvent;
import com.example.events.OrderShippedEvent;
import com.example.events.OrderUpdatedEvent;
import com.example.events.ShipmentDeniedEvent;

@Saga
public class OrderManagementSaga {

    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){
        String paymentId = UUID.randomUUID().toString();
        System.out.println("[SAGA-Start] OrderCreatedEvent: "+orderCreatedEvent.orderId);
        
        //associate Saga
        SagaLifecycle.associateWith("paymentId", paymentId);

        //send the commands
        commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId));
    }

    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(InvoiceCreatedEvent invoiceCreatedEvent){
        String shippingId = UUID.randomUUID().toString();

        System.out.println("[SAGA] InvoiceCreatedEvent: "+invoiceCreatedEvent.orderId);
        
        //associate Saga with shipping
        SagaLifecycle.associateWith("shipping", shippingId);

        //send the create shipping command
        commandGateway.send(new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent orderShippedEvent){

        System.out.println("[SAGA] OrderShippedEvent: "+orderShippedEvent.orderId);
        commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId, String.valueOf(OrderStatus.SHIPPED)));
    }
    
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ShipmentDeniedEvent shipmentDeniedEvent){
        System.out.println("[SAGA] ShipmentDeniedEvent: "+shipmentDeniedEvent.orderId);
        commandGateway.send(new CancelInvoiceCommand(shipmentDeniedEvent.paymentId,shipmentDeniedEvent.orderId));
        commandGateway.send(new UpdateOrderStatusCommand(shipmentDeniedEvent.orderId, String.valueOf(OrderStatus.REJECTED)));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent orderUpdatedEvent){
        System.out.println("[SAGA-End] OrderUpdatedEvent: "+orderUpdatedEvent.orderId);
        SagaLifecycle.end();
    }
}
