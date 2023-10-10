package com.appdynamics.demo.smartticket.order.api;

import java.util.UUID;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.appdynamics.demo.smartticket.order.api.model.OrderRequest;
import com.appdynamics.demo.smartticket.order.api.model.OrderResponse;

import io.smallrye.mutiny.Multi;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/order")
public class OrderResource {

    @Channel("order-requests")
    Emitter<OrderRequest> ticketRequestEmitter;

    @Channel("order-responses")
    Multi<JsonObject> ticketResponse;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest(@QueryParam("clientId") String clientId, @QueryParam("eventId") String eventId)
            throws InterruptedException {
        UUID uuid = UUID.randomUUID();
        Thread.sleep(500);
        ticketRequestEmitter.send(new OrderRequest(uuid.toString(), clientId, eventId));
        return uuid.toString();
    }

    @GET
    @Path("/info") 
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public String info() {
        String message = ConfigProvider.getConfig().getValue("greeting.message", String.class);
        System.out.println("message is: " + message);
        return message;
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<OrderResponse> stream(@QueryParam("clientId") String clientId) {
        return ticketResponse.select()
                .where(i -> clientId.equals(i.getString("clientId")))
                .map(json -> json.mapTo(OrderResponse.class));
    }
}
