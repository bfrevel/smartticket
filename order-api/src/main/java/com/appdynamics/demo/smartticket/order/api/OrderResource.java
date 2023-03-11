package com.appdynamics.demo.smartticket.order.api;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.appdynamics.demo.smartticket.order.api.model.OrderRequest;
import com.appdynamics.demo.smartticket.order.api.model.OrderResponse;

import io.smallrye.mutiny.Multi;
import io.vertx.core.json.JsonObject;

@Path("/order")
public class OrderResource {

    @Channel("order-requests")
    Emitter<OrderRequest> ticketRequestEmitter;

    @Channel("order-responses")
    Multi<JsonObject> ticketResponse;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest(@QueryParam("clientId") String clientId, @QueryParam("clientId") String eventId)
            throws InterruptedException {
        UUID uuid = UUID.randomUUID();
        Thread.sleep(500);
        ticketRequestEmitter.send(new OrderRequest(uuid.toString(), clientId, eventId));
        return uuid.toString();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<OrderResponse> stream(@QueryParam("clientId") String clientId) {
        return ticketResponse.select()
                .where(i -> clientId.equals(i.getString("clientId")))
                .map(json -> json.mapTo(OrderResponse.class));
    }
}
