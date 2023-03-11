package com.appdynamics.demo.smartticket.order.processor.model;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class OrderRequest {

    public String id;
    public String clientId;
    public String eventId;

    public OrderRequest() {
    }

    public OrderRequest(String id, String clientId, String eventId) {
        this.id = id;
        this.clientId = clientId;
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "OrderRequest [id=" + id + ", clientId=" + clientId + ", eventId=" + eventId + "]";
    }

}