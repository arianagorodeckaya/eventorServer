package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.mapper.EventMapper;
import com.eventor.haradzetskaya.model.EventDTO;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin/events")
public class EventAdminController {

    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;
    @Autowired
    EventMapper eventMapper;

    @GetMapping
    public Page<EventDTO> getEvents(@RequestParam(required = false) String confirmation, @RequestParam(defaultValue = "0") int page) {
        Page<Event> events;
        if (confirmation!=null) {
            events = this.eventService.getByConfirmation(confirmation,page);
        }
        else
            events = this.eventService.getAllEvents(page);
        return new PageImpl<>(events.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(path = "/{id}")
    EventDTO approveEvent(@PathVariable int id, @RequestParam String approve) {
        Event oldEvent = eventService.getById(id);
        Event newEvent;
        switch (approve) {
            case "yes":
                oldEvent.setConfirmation(true);
                newEvent = eventService.saveEvent(oldEvent);
                break;
            case "no":
                oldEvent.setConfirmation(false);
                newEvent = eventService.saveEvent(oldEvent);
                break;
            default:
                newEvent = oldEvent;
        }
        return eventMapper.toDto(newEvent);
    }

}
