package com.appdynamics.demo.smartticket.order.processor.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderResponse {

    public String id;
    public String clientId;
    public String eventId;
    public int price;

    public OrderResponse() {
    }

    public OrderResponse(OrderRequest ticketRequest, int price) {
        this.id = ticketRequest.id;
        this.clientId = ticketRequest.clientId;
        this.eventId = ticketRequest.eventId;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderResponse [id=" + id + ", clientId=" + clientId + ", eventId=" + eventId + ", price=" + price
                + "]";
    }

}