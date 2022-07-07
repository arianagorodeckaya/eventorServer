package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public Map<String, Long> getStatistics(@RequestParam String setting) {
        Map<String, Long> statistics = new HashMap<>();
        switch (setting) {
            case "price":
                Long free = eventService.getCountFree();
                Long paid = eventService.getCountPaid();
                statistics.put("Free", free);
                statistics.put("Paid", paid);
                break;
            case "status":
                Long countScheduled = eventService.getCountScheduled();
                Long countEnded = eventService.getCountEnded();
                Long countInProcess = eventService.getCountInProcess();
                statistics.put("Scheduled", countScheduled);
                statistics.put("In Process", countInProcess);
                statistics.put("Ended", countEnded);
                break;
            case "entities":
                Long users = userService.getCountUsers();
                Long events = eventService.getCountEvents();
                statistics.put("Users", users);
                statistics.put("Events", events);
                break;
        }
        return statistics;
    }
}
