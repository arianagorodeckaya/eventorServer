package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventRepository {
    public Event findById(int id);
    public List<Event> findAll();
    public List<Event> findActiveAll();
    public List<Event> findExpiredAll();
    public Event saveEvent(Event event);
    public Event updateEvent(Event event);
    public void deleteEvent(int id);
    public Page<Event> findConfirmedAll(Pageable pageable);
    public Page<Event> findUnconfirmedAll(Pageable pageable);
}
