package com.appdynamics.demo.smartticket.event.api.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Event {

    public UUID id;
    public String name;
    public String city;
    public String location;
    public LocalDateTime begin;

    public Event() {
    }

    public Event(UUID id, String name, String city, String location, LocalDateTime begin) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.location = location;
        this.begin = begin;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", city=" + city + ", location=" + location + ", begin=" + begin
                + "]";
    }

}
