package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.model.ErrorResponse;
import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.myEnum.Role;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(path = "/users")
    public Page<User> getAll(@RequestParam(defaultValue = "0") int page) {
        Page<User> users = this.userService.getAll(page);
        for (User user: users) {
            user.setCreatorEvents(userService.setOnlyIdForUser(user));
            for (Event event : user.getEvents()) {
                event.setUsers(eventService.setOnlyIdForUsers(event));
                event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
            }
        }
        return users;
    }

    @DeleteMapping(path = "/user")
    ResponseEntity<?> deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "User was deleted", System.currentTimeMillis()));
    }

    @GetMapping(path = "/user")
    User getUser(@RequestBody User user) {
        User outUser = userService.getById(user.getId());
        outUser.setCreatorEvents(userService.setOnlyIdForUser(outUser));
        for (Event event : outUser.getEvents()) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(this.eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return outUser;
    }

    @PostMapping(path = "/save")
    User saveUser(@RequestBody User newUser) {
        newUser.setRole(Role.ADMIN);
        newUser.setId(0);
        newUser.setPw_hash(passwordEncoder.encode(newUser.getPw_hash()));
        return userService.saveUser(newUser);
    }
}
