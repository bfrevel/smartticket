package com.appdynamics.demo.smartticket.event.api;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.appdynamics.demo.smartticket.event.api.model.Event;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventDatabase {

  @Inject
  private PgPool client;

  @Inject
  @ConfigProperty(name = "schema.create", defaultValue = "true")
  boolean schemaCreate;

  void config(@Observes StartupEvent ev) {
    if (schemaCreate) {
      initdb();
    }
  }

  private void initdb() {
    client.query("DROP TABLE IF EXISTS event").execute()
        .flatMap(r -> client.query(
            "CREATE TABLE event (id UUID PRIMARY KEY, name TEXT NOT NULL, city TEXT NOT NULL, location TEXT NOT NULL, begin TIMESTAMP)")
            .execute())
        .flatMap(r -> client.query(
            "INSERT INTO event (id, name, city, location, begin) VALUES ('92569260-e7d6-44ce-8f7d-da969b847891', 'MUSE', 'Bern', 'Bernexpo Areal Openair', '2023-07-12 16:00:00')")
            .execute())
        .flatMap(r -> client.query(
            "INSERT INTO event (id, name, city, location, begin) VALUES ('d09a6a4a-9849-4619-ab39-a69961597c78', 'dEUS', 'Zürich', 'Kaufleuten', '2023-03-28 19:00:00')")
            .execute())
        .flatMap(r -> client.query(
            "INSERT INTO event (id, name, city, location, begin) VALUES ('59e8ee78-fc85-4bdb-9822-4c57023cb0b6', 'Fettes Brot', 'Zürich', 'The Hall', '2023-04-26 18:00:00')")
            .execute())
        .flatMap(r -> client.query(
            "INSERT INTO event (id, name, city, location, begin) VALUES ('e9e93bd5-c8b5-4513-8f8d-f5c8515ccebf', 'Irish Folk Festival 2023', 'Basel', 'Volkshaus Basel', '2023-10-28 17:30:00')")
            .execute())
        .await().indefinitely();
  }

  private static Event from(Row row) {
    return new Event(row.getUUID("id"), row.getString("name"), row.getString("city"), row.getString("location"),
        row.getLocalDateTime("begin"));
  }

  public Multi<Event> findAll() {
    Log.infof("[database] get all events");
    return client.query("SELECT id, name, city, location, begin FROM event ORDER BY begin ASC").execute()
        .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
        .onItem().transform(EventDatabase::from);
  }

  public Uni<Event> findById(String uuid) {
    Log.infof("[database] get event {%s}", uuid);
    return client.preparedQuery("SELECT id, name, city, location, begin FROM event where id = $1")
        .execute(Tuple.of(uuid))
        .onItem().transform(RowSet::iterator)
        .onItem().transform(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
  }

}
