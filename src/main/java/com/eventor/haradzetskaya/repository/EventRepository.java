package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;

import java.util.List;

public interface EventRepository {
    public Event getById(int id);
    public List<Event> getAll();
    public Event saveEvent(Event event);
    public Event updateEvent(User user);
    public void deleteEvent(int id);
}
