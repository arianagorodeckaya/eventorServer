package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;

import java.util.List;

public interface EventService {

    public List<Event> getAll();
    public List<Event> getActiveAll();
    public List<Event> getExpiredAll();
    public List<Event> getMyActiveAll(String email);
    public List<Event> getMyExpiredAll(String email);
    public Event saveEvent(Event Event);
    public Event getById(int id);
    public Event updateEvent(Event Event);
    public void deleteEvent(int id);
    public List<User> setOnlyIdForUsers(Event event);
    public User setOnlyIdForCreator(User user);
}
