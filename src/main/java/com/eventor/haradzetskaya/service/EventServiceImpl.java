package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.mapper.EventMapper;
import com.eventor.haradzetskaya.mapper.UserMapper;
import com.eventor.haradzetskaya.repository.EventRepository;
import com.eventor.haradzetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<Event> getActiveAll() {
        return eventRepository.findByEndDateIsGreaterThan(new Date(System.currentTimeMillis()));
    }

    @Override
    public List<Event> getActiveAndApprovedAll() {
        return eventRepository.findByEndDateIsGreaterThanAndConfirmation(new Date(System.currentTimeMillis()), true);
    }

    @Override
    public List<Event> getExpiredAll() {
        return eventRepository.findByEndDateIsLessThan(new Date(System.currentTimeMillis()));
    }

    @Override
    public List<Event> getMyActiveAll(String email) {
        User myUser = userRepository.findByEmail(email);
        List<Event> myActiveEvents = eventRepository.findByEndDateIsGreaterThanAndCreator(new Date(System.currentTimeMillis()), myUser);
        return myActiveEvents;
    }

    @Override
    public List<Event> getMyExpiredAll(String email) {
        User myUser = userRepository.findByEmail(email);
        List<Event> myExpiredEvents = eventRepository.findByEndDateIsLessThanAndCreator(new Date(System.currentTimeMillis()), myUser);
        return myExpiredEvents;
    }

    @Override
    public Event saveEvent(Event event) {
        if (event.getId() != 0) {
            Optional<Event> eventOptional = eventRepository.findById(event.getId());
            if (!eventOptional.isPresent()) {
                throw new RuntimeException("Event with id = " + event.getId() + " not found");
            }
        }
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event getById(int id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        Event event = null;
        if (eventOptional.isPresent()) {
            event = eventOptional.get();
        } else
            throw new RuntimeException("Event with id = " + event.getId() + " not found");

        return event;
    }

    @Override
    public void deleteEvent(int id) {
       eventRepository.deleteById(id);
    }

    @Override
    public Page<Event> getByConfirmation(String confirmation, int page) {
        Pageable pageable = PageRequest.of(page, 20);
        if (confirmation.equals("null") || confirmation.equals(null))
            return eventRepository.findByConfirmation(null,pageable);
        return eventRepository.findByConfirmation(Boolean.valueOf(confirmation),pageable);
    }

    @Override
    public Page<Event> getAllEvents(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return eventRepository.findAll(pageable);
    }

    @Override
    public Long getCountFree() {
        return eventRepository.countEventsByPriceIsLessThanEqual(0);
    }

    @Override
    public Long getCountPaid() {
        return eventRepository.countEventsByPriceIsGreaterThan(0);
    }

    @Override
    public Long getCountScheduled() {
        return eventRepository.countEventsByStartDateIsGreaterThan(new Date(System.currentTimeMillis()));
    }

    @Override
    public Long getCountInProcess() {
        return eventRepository.countEventsByStartDateIsLessThanAndEndDateIsGreaterThan(new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
    }

    @Override
    public Long getCountEnded() {
        return eventRepository.countEventsByEndDateIsLessThan(new Date(System.currentTimeMillis()));
    }

    @Override
    public Long getCountEvents() {
        return eventRepository.count();
    }
}
