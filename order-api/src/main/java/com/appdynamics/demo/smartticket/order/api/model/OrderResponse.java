package com.appdynamics.demo.smartticket.order.api.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderResponse {

    public String id;
    public String clientId;
    public String eventId;
    public int price;

    public OrderResponse() {
    }

    public OrderResponse(String id, String clientId, String eventId, int price) {
        this.id = id;
        this.clientId = clientId;
        this.eventId = eventId;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderRequest [id=" + id + ", clientId=" + clientId + ", eventId=" + eventId + ", price=" + price
                + "]";
    }

}