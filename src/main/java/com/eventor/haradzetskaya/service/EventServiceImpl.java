package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.repository.EventRepository;
import com.eventor.haradzetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;

    @Override
    public List<Event> getAll() {
        return null;
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.saveEvent(event);
    }

    @Override
    public Event getById(int id) {
        return eventRepository.getById(id);
    }

    @Override
    public Event updateEvent(Event Event) {
        return null;
    }

    @Override
    public void deleteEvent(int id) {

    }
}
