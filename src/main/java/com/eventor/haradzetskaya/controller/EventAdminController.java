package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.mapper.EventMapper;
import com.eventor.haradzetskaya.model.EventDTO;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path = "/confirmed")
    public Page<EventDTO> getAllConfirmedEvents(@RequestParam(defaultValue = "0") int page) {
        Page<Event> events = this.eventService.getConfirmedAll(page);
        return new PageImpl<>(events.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(path = "/unconfirmed")
    public Page<EventDTO> getAllUnconfirmedEvents(@RequestParam(defaultValue = "0") int page) {
        Page<Event> events = this.eventService.getUnconfirmedAll(page);
        return new PageImpl<>(events.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping(path = "/nullconfirmed")
    public Page<EventDTO> getAllNullConfirmedEvents(@RequestParam(defaultValue = "0") int page) {
        Page<Event> events = this.eventService.getNullConfirmedEvents(page);
        return new PageImpl<>(events.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

    @GetMapping
    public Page<EventDTO> getAllEvents(@RequestParam(defaultValue = "0") int page) {
        Page<Event> events = this.eventService.getAllEvents(page);
        return new PageImpl<>(events.stream().map(eventMapper::toDto).collect(Collectors.toList()));
    }

}
