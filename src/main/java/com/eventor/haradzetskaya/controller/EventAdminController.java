package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/event")
public class EventAdminController {

    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    @GetMapping(path = "/confirmed")
    public Page<Event> getAllConfirmedEvent(@RequestParam(defaultValue = "0") int page) {
        Page<Event> events = this.eventService.getConfirmedAll(page);
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @GetMapping(path = "/unconfirmed")
    public Page<Event> getAllUnconfirmedEvent(@RequestParam(defaultValue = "0") int page) {
        Page<Event> events = this.eventService.getUnconfirmedAll(page);
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

    @GetMapping(path = "/nullconfirmed")
    public Page<Event> getAllNullConfirmedEvent(@RequestParam(defaultValue = "0") int page) {
        Page<Event> events = this.eventService.getNullConfirmedEvents(page);
        for (Event event:events) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return events;
    }

}
