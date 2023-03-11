package com.appdynamics.demo.smartticket.order.processor;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import com.appdynamics.demo.smartticket.order.processor.model.OrderRequest;
import com.appdynamics.demo.smartticket.order.processor.model.OrderResponse;

import io.vertx.core.json.JsonObject;

import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
public class OrderProcessor {

    private Random random = new Random();

    @Incoming("order-requests")
    @Outgoing("order-responses")
    @Blocking
    public OrderResponse process(JsonObject json) throws InterruptedException {
        OrderRequest orderRequest = json.mapTo(OrderRequest.class);
        Thread.sleep(2000);
        return new OrderResponse(orderRequest, random.nextInt(100));
    }
}
