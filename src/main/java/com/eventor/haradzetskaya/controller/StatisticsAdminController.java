package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/statistics")
public class StatisticsAdminController {
    @Autowired
    EventService eventService;
    @Autowired
    UserService userService;

    @GetMapping(path = "/price")
    public Map<String, Long> getStatisticsPrice() {
        Map<String, Long> prices = new HashMap<>();
        Long free = eventService.getCountFree();
        Long paid = eventService.getCountPaid();
        prices.put("Free", free);
        prices.put("Paid", paid);
        System.out.println(prices);
        return prices;
    }

    @GetMapping(path = "/status")
    public Map<String, Long> getStatisticsStatus() {
        Map<String, Long> status = new HashMap<>();
        Long countScheduled = eventService.getCountScheduled();
        Long countEnded = eventService.getCountEnded();
        Long countInProcess = eventService.getCountInProcess();
        status.put("Scheduled", countScheduled);
        status.put("In Process", countInProcess);
        status.put("Ended", countEnded);
        return status;
    }

    @GetMapping(path = "/entities")
    public Map<String, Long> getStatisticsEntities() {
        Map<String, Long> entities = new HashMap<>();
        Long users = userService.getCountUsers();
        Long events = eventService.getCountEvents();
        entities.put("Users", users);
        entities.put("Events", events);
        return entities;
    }

}
