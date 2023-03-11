package com.appdynamics.demo.smartticket.event.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.reactive.Cache;

import com.appdynamics.demo.smartticket.event.api.model.Event;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

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
