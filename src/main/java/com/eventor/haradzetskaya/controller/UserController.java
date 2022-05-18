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

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@RequestBody User inpUser) {
        User outUser = this.userService.getByEmail(inpUser.getEmail());
        outUser.setCreatorEvents(userService.setOnlyIdForUser(outUser));
        for (Event event:outUser.getEvents()) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return outUser;
    }

    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getMyUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
       user.setCreatorEvents(userService.setOnlyIdForUser(user));
        for (Event event:user.getEvents()) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return user;
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    User deleteMyUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        userService.deleteUser(user.getId());
        return user;
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    User deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
        return user;
    }
}
