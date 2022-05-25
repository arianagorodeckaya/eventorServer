package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.repository.EventRepository;
import com.eventor.haradzetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    EventRepository eventRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Event> getActiveAll() {
        return eventRepository.findActiveAll();
    }

    @Override
    public List<Event> getExpiredAll() {
        return eventRepository.findExpiredAll();
    }

    @Override
    public List<Event> getMyActiveAll(String email) {
        User myUser = userRepository.findByEmail(email);
        List<Event> myActiveEvents = eventRepository.findMyActiveAll(myUser.getId());
        return myActiveEvents;
    }

    @Override
    public List<Event> getMyExpiredAll(String email) {
        User myUser = userRepository.findByEmail(email);
        List<Event> myExpiredEvents = eventRepository.findMyExpiredAll(myUser.getId());
        return myExpiredEvents;
    }

    @Override
    public Event saveEvent(Event event) {
        if(event.getId()!=0) {
            Event oldEvent = eventRepository.findById(event.getId());
            oldEvent.setName(event.getName());
            oldEvent.setArchive(event.isArchive());
            oldEvent.setConfirmation(event.getConfirmation());
            oldEvent.setStartDate(event.getStartDate());
            oldEvent.setEndDate(event.getEndDate());
            oldEvent.setDescription(event.getDescription());
            oldEvent.setImage(event.getImage());
            oldEvent.setPrice(event.getPrice());
            //oldEvent.setCreator(event.getCreator());
            oldEvent.setLatitude(event.getLatitude());
            oldEvent.setLongitude(event.getLongitude());
            if(oldEvent.getUsers()!=null) {
                oldEvent.setUsers(event.getUsers());
            }
            event = oldEvent;
        }
        return eventRepository.saveEvent(event);
    }

    @Override
    public Event getById(int id) {
        return eventRepository.findById(id);
    }

    @Override
    public void deleteEvent(int id) {
       eventRepository.deleteEvent(id);
    }

    @Override
    public List<User> setOnlyIdForUsers(Event event) {
        List<User> users = event.getUsers();
        List<User> newUsers = new ArrayList<>();
        for (User user: users){
            int id = user.getId();
            User newUser = new User();
            newUser.setId(id);
            newUsers.add(newUser);
        }
        return newUsers;
    }

    @Override
    public User setOnlyIdForCreator(User user) {
        int id = user.getId();
        User newUser = new User();
        newUser.setId(id);
        return newUser;
    }

    @Override
    public Page<Event> getConfirmedAll(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return eventRepository.findConfirmedAll(pageable);
    }

    @Override
    public Page<Event> getUnconfirmedAll(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return eventRepository.findUnconfirmedAll(pageable);
    }

    @Override
    public Page<Event> getNullConfirmedEvents(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return eventRepository.findNullConfirmedEvents(pageable);
    }

    @Override
    public Page<Event> getAllEvents(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return eventRepository.findAll(pageable);
    }

    @Override
    public Long getCountFree() {
        return eventRepository.countFree();
    }

    @Override
    public Long getCountPaid() {
        return eventRepository.countPaid();
    }

    @Override
    public Long getCountScheduled() {
        return eventRepository.countScheduled();
    }

    @Override
    public Long getCountInProcess() {
        return eventRepository.countInProcess();
    }

    @Override
    public Long getCountEnded() {
        return eventRepository.countEnded();
    }

    @Override
    public Long getCountEvents() {
        return eventRepository.countEvents();
    }
}
