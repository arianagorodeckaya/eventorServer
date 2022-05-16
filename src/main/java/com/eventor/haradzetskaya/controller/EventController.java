package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Event getEvent(@RequestBody Event inpEvent) {
        Event outEvent = this.eventService.getById(inpEvent.getId());
        outEvent.setUsers(this.eventService.setOnlyIdForUsers(outEvent));
        outEvent.setCreator(this.eventService.setOnlyIdForCreator(outEvent.getCreator()));
        return outEvent;
    }

    @GetMapping(path = "/get/all/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllActiveEvent() {
        List<Event> events = this.eventService.getActiveAll();
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @GetMapping(path = "/get/my/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getMyActiveEvent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Event> events = this.eventService.getMyActiveAll(auth.getName());
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @GetMapping(path = "/get/my/expired", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getMyExpiredEvent(@RequestBody Event inpEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Event> events = this.eventService.getMyExpiredAll(auth.getName());
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    void saveEvent(@RequestBody Event newEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        newEvent.setCreator(user);
        eventService.saveEvent(newEvent);
    }

    @PostMapping(path = "/subscribe", produces = MediaType.APPLICATION_JSON_VALUE)
    void subscribeEvent(@RequestBody Event newEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        List<User> users = newEvent.getUsers();
        if(newEvent.getUsers()==null){
            users=new ArrayList<>();
            users.add(user);
        }
        else
            users.add(user);
        eventService.updateEvent(newEvent);
    }
}
