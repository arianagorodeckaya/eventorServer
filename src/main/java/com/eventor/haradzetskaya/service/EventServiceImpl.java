package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.repository.EventRepository;
import com.eventor.haradzetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Event> getAll() {
        return null;
    }

    @Override
    public List<Event> getActiveAll() {
        return eventRepository.getActiveAll();
    }

    @Override
    public List<Event> getExpiredAll() {
        return eventRepository.getExpiredAll();
    }

    @Override
    public List<Event> getMyActiveAll(String email) {
        List<Event> events = eventRepository.getActiveAll();
        User myUser = userRepository.getByEmail(email);
        List<Event> myActiveEvents = new ArrayList<>();
        for (Event event:events) {
            for (User user: event.getUsers()) {
                if(user.equals(myUser)) {
                    myActiveEvents.add(event);
                    break;
                }
            }
        }
        return myActiveEvents;
    }

    @Override
    public List<Event> getMyExpiredAll(String email) {
        List<Event> events = eventRepository.getExpiredAll();
        User myUser = userRepository.getByEmail(email);
        List<Event> myExpiredEvents = new ArrayList<>();
        for (Event event:events) {
            for (User user: event.getUsers()) {
                if(user.equals(myUser)) {
                    myExpiredEvents.add(event);
                    break;
                }
            }
        }
        return myExpiredEvents;
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
    public Event updateEvent(Event event) {
        return eventRepository.updateEvent(event);
    }

    @Override
    public void deleteEvent(int id) {

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
}
