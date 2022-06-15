package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventRepository {
    public Event findById(int id);
    public Page<Event> findAll(Pageable pageable);
    public List<Event> findActiveAll();
    public List<Event> findActiveAndApprovedAll();
    public List<Event> findExpiredAll();
    public Event saveEvent(Event event);
    public void deleteEvent(int id);
    public Page<Event> findConfirmedAll(Pageable pageable);
    public Page<Event> findUnconfirmedAll(Pageable pageable);
    public Page<Event> findNullConfirmedEvents(Pageable pageable);
    public Long countFree();
    public Long countPaid();
    public Long countScheduled();
    public Long countInProcess();
    public Long countEnded();
    public Long countEvents();
    public List<Event> findMyActiveAll(int id);
    public List<Event> findMyExpiredAll(int id);
}
