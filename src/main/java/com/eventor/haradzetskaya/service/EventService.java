package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;

import java.util.List;

public interface EventService {

    public List<Event> getAll();
    public Event saveEvent(Event Event);
    public Event getById(int id);
    public Event updateEvent(Event Event);
    public void deleteEvent(int id);
}
