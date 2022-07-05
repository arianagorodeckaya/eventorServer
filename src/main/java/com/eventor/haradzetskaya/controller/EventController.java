package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.exceptionHandler.NotFoundException;
import com.eventor.haradzetskaya.mapper.EventMapper;
import com.eventor.haradzetskaya.mapper.UserMapper;
import com.eventor.haradzetskaya.model.*;
import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    UserMapper userMapper;

    @GetMapping(path = "/{id}")
    public EventDTO getEvent(@PathVariable int id) {
        Event outEvent = this.eventService.getById(id);
        if(outEvent==null)
            throw new NotFoundException("Event with id not found - " + id);
        return eventMapper.toDto(outEvent);
    }

    @GetMapping(path = "/active")
    public List<EventDTO> getAllActiveEvent() {
        List<Event> events = this.eventService.getActiveAndApprovedAll();
        return events.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/my/active")
    public List<EventDTO> getMyActiveEvent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Event> events = this.eventService.getMyActiveAll(auth.getName());
        return events.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/my/expired")
    public List<EventDTO> getMyExpiredEvent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Event> events = this.eventService.getMyExpiredAll(auth.getName());
        return events.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/subscriptions")
    public List<EventDTO> getMySubscriptions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User myUser = this.userService.getByEmail(auth.getName());
        List<Event> events = myUser.getEvents();
        return events.stream().map(eventMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}/subscribers")
    public List<UserDTO> getSubscribersEvent(@PathVariable int id) {
        List<User> subscribers = this.eventService.getById(id).getUsers();
        return subscribers.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping
    ResponseEntity<?> saveEvent(@RequestBody Event newEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        newEvent.setId(0);
        newEvent.setCreator(user);
        newEvent.setArchive(false);
        newEvent.setUsers(new ArrayList<>());
        eventService.saveEvent(newEvent);
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "Event was added", System.currentTimeMillis()));
    }

    @PostMapping(path = "/{id}/subscribe")
    void subscribeEvent(@PathVariable int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        Event event = eventService.getById(id);
        user.addEvents(event);
        eventService.saveEvent(event);
    }

    @PutMapping
    EventDTO updateEvent(@RequestBody EventDTO eventDTO) {
        Event newEvent = eventService.saveEvent(eventMapper.toEvent(eventDTO));
        return eventMapper.toDto(newEvent);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<?> deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        if(eventService.getById(id)!=null)
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Event wasn't deleted", System.currentTimeMillis()));
        return ResponseEntity.ok("Event was deleted");
    }

}
