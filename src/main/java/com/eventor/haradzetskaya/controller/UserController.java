package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
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
    private PasswordEncoder passwordEncoder;

    @GetMapping(path = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@RequestBody User inpUser) {
        User outUser = this.userService.getByEmail(inpUser.getEmail());
        outUser.setCreatorEvents(userService.setOnlyIdForUser(outUser));
        return outUser;
    }

    @GetMapping(path = "/get/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getMyUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
       user.setCreatorEvents(userService.setOnlyIdForUser(user));
        return user;
    }

    @PutMapping(path = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    User deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        userService.deleteUser(user.getId());
        return user;
    }



}
