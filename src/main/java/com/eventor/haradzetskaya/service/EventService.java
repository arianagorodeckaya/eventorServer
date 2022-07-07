package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.entity.Event;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EventService {

    public List<Event> getActiveAll();
    public List<Event> getExpiredAll();
    public List<Event> getMyActiveAll(String email);
    public List<Event> getMyExpiredAll(String email);
    public Event saveEvent(Event event);
    public Event getById(int id);
    public void deleteEvent(int id);
    public Page<Event> getAllEvents(int page);
    public Long getCountFree();
    public Long getCountPaid();
    public Long getCountScheduled();
    public Long getCountInProcess();
    public Long getCountEnded();
    public Long getCountEvents();
    public List<Event> getActiveAndApprovedAll();
    public Page<Event> getByConfirmation(String confirmation, int page);
}
