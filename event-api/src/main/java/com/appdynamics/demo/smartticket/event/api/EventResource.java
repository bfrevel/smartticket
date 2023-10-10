package com.appdynamics.demo.smartticket.event.api;

import org.jboss.resteasy.reactive.Cache;

import com.appdynamics.demo.smartticket.event.api.model.Event;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;

@Path("event")
public class EventResource {

  private final EventService eventService;

  public EventResource(EventService eventService) {
    this.eventService = eventService;
  }

  @GET
  @Cache
  public Multi<Event> get() {
    return eventService.findAll();
  }

  @GET
  @Path("/{uuid}")
  @Cache(maxAge = 3600, sMaxAge = 1800, mustRevalidate = true, proxyRevalidate = true)
  public Uni<Response> get(String uuid) {
    return eventService.findById(uuid)
        .onItem().transform(event -> event != null ? Response.ok(event) : Response.status(Status.NOT_FOUND))
        .onItem().transform(ResponseBuilder::build);
  }

}
