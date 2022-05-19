package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.exceptionHandler.NotFoundException;
import com.eventor.haradzetskaya.model.ErrorResponse;
import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
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

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public User getUser(@RequestBody User inpUser) {
        User outUser = this.userService.getByEmail(inpUser.getEmail());
        if(outUser==null) {
            throw new NotFoundException("User with email not found - " + inpUser.getEmail());
        }
        outUser.setCreatorEvents(userService.setOnlyIdForUser(outUser));
        for (Event event:outUser.getEvents()) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(this.eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return outUser;
    }

    @GetMapping(path = "/me")
    public User getMyUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        user.setCreatorEvents(userService.setOnlyIdForUser(user));
        for (Event event : user.getEvents()) {
            event.setUsers(this.eventService.setOnlyIdForUsers(event));
            event.setCreator(eventService.setOnlyIdForCreator(event.getCreator()));
        }
        return user;
    }

    @PutMapping
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping(path = "/me")
    ResponseEntity<?>  deleteMyUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getByEmail(email);
        userService.deleteUser(user.getId());
        if(userService.getByEmail(email)!=null)
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User wasn't deleted", System.currentTimeMillis()));
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "User was deleted", System.currentTimeMillis()));
    }

    @DeleteMapping
    ResponseEntity<?>  deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
        if(userService.getById(user.getId())!=null)
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User wasn't deleted", System.currentTimeMillis()));
        return ResponseEntity.ok(new ErrorResponse(HttpStatus.OK.value(), "User was deleted", System.currentTimeMillis()));
    }
}
