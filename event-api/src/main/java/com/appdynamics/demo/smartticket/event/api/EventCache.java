package com.appdynamics.demo.smartticket.event.api;

import javax.enterprise.context.ApplicationScoped;

import com.appdynamics.demo.smartticket.event.api.model.Event;

import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.value.ReactiveValueCommands;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class EventCache {

    private ReactiveValueCommands<String, Event> values;

    public EventCache(ReactiveRedisDataSource reactiveRedisDataSource) {
        this.values = reactiveRedisDataSource.value(Event.class);
    }

    Uni<Event> get(String key) {
        Log.infof("[redis] get event {%s}", key);
        return values.get(key);
    }

    Uni<Void> set(String key, Event value) {
        Log.infof("[redis] set event {%s} to value {%s}", key, value);
        return values.setex(key, 30, value);
    }

}
