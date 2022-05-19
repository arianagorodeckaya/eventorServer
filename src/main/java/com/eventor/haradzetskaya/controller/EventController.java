package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.exceptionHandler.NotFoundException;
import com.eventor.haradzetskaya.model.ErrorResponse;
import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.myEnum.Status;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    @GetMapping
    public Event getEvent(@RequestBody Event inpEvent) {
        Event outEvent = this.eventService.getById(inpEvent.getId());
        if(outEvent==null)
            throw new NotFoundException("Event with id not found - " + inpEvent.getId());
        outEvent.setUsers(this.eventService.setOnlyIdForUsers(outEvent));
        outEvent.setCreator(this.eventService.setOnlyIdForCreator(outEvent.getCreator()));
        return outEvent;
    }

    @GetMapping(path = "/all/active")
    public List<Event> getAllActiveEvent() {
        List<Event> events = this.eventService.getActiveAll();
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @GetMapping(path = "/my/active")
    public List<Event> getMyActiveEvent() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Event> events = this.eventService.getMyActiveAll(auth.getName());
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @GetMapping(path = "/my/expired")
    public List<Event> getMyExpiredEvent(@RequestBody Event inpEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Event> events = this.eventService.getMyExpiredAll(auth.getName());
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @PostMapping
    ResponseEntity<?> saveEvent(@RequestBody Event newEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        newEvent.setCreator(user);
        newEvent.setStatus(Status.SCHEDULE);
        newEvent.setArchive(false);
        eventService.saveEvent(newEvent);
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "Event was added", System.currentTimeMillis()));
    }

    @PostMapping(path = "/subscribe")
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

    @PutMapping
    Event updateEvent(@RequestBody Event event) {
        return eventService.updateEvent(event);
    }

    @DeleteMapping
    ResponseEntity<?> deleteEvent(@RequestBody Event event) {
        eventService.deleteEvent(event.getId());
        if(eventService.getById(event.getId())!=null)
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Event wasn't deleted", System.currentTimeMillis()));
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "Event was deleted", System.currentTimeMillis()));
    }

}
