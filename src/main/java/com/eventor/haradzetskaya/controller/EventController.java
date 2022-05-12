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
        return outEvent;
    }

    @PostMapping(path = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    void saveEvent(@RequestBody Event newEvent) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        newEvent.setCreator(user);
        eventService.saveEvent(newEvent);
    }
}
