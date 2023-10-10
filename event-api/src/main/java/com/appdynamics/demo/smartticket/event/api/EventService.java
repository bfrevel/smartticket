package com.appdynamics.demo.smartticket.event.api;

import com.appdynamics.demo.smartticket.event.api.model.Event;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventService {

  @Inject
  private EventCache cacheReactive;

  @Inject
  private EventDatabase eventDatabase;

  public Multi<Event> findAll() {
    return eventDatabase.findAll();
  }

  public Uni<Event> findById(String uuid) {
    return cacheReactive.get(uuid)
        .onItem().ifNull()
        .switchTo(
            () -> eventDatabase.findById(uuid).onItem().ifNotNull().call(event -> cacheReactive.set(uuid, event)));
  }

}
