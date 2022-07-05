package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.mapper.EventMapper;
import com.eventor.haradzetskaya.mapper.UserMapper;
import com.eventor.haradzetskaya.model.*;
import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.enums.Role;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserMapper userMapper;
    @Autowired
    EventMapper eventMapper;


    @GetMapping(path = "/users")
    public Page<UserDTO> getAll(@RequestParam(defaultValue = "0") int page) {
        Page<User> users = this.userService.getAll(page);
        return new PageImpl<>(users.stream().map(userMapper::toDto).collect(toList()));
    }

    @DeleteMapping(path = "/users/{id}")
    ResponseEntity<?> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User was deleted");
    }

    @GetMapping(path = "/users/{id}")
    UserDTO getUser(@PathVariable int id) {
        User outUser = userService.getById(id);
        return userMapper.toDto(outUser);
    }

    @PostMapping(path = "/save")
    UserDTO saveUser(@RequestBody UserSecurityDTO newUser) {
        ModelMapper modelMapper = new ModelMapper();
        newUser.setRole(Role.ADMIN);
        newUser.setId(0);
        newUser.setPwHash(passwordEncoder.encode(newUser.getPwHash()));
        return userMapper.toDto(modelMapper.map(newUser, User.class));

    }

    @PostMapping(path = "/approve/{id}")
    EventDTO approveEvent(@PathVariable int id) {
        Event oldEvent = eventService.getById(id);
        oldEvent.setConfirmation(true);
        Event newEvent = eventService.saveEvent(oldEvent);
        return eventMapper.toDto(newEvent);
    }

    @PostMapping(path = "/decline/{id}")
    EventDTO declineEvent(@PathVariable int id) {
        Event oldEvent = eventService.getById(id);
        oldEvent.setConfirmation(false);
        Event newEvent = eventService.saveEvent(oldEvent);
        return eventMapper.toDto(newEvent);
    }
}
