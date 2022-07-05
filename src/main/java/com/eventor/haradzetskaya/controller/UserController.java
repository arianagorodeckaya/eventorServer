package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.exceptionHandler.NotFoundException;
import com.eventor.haradzetskaya.mapper.UserMapper;
import com.eventor.haradzetskaya.mapper.UserSecurityMapper;
import com.eventor.haradzetskaya.model.ErrorResponse;
import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.model.UserDTO;
import com.eventor.haradzetskaya.model.UserSecurityDTO;
import com.eventor.haradzetskaya.service.EventService;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    EventService eventService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserSecurityMapper userSecurityMapper;

    @GetMapping
    public UserDTO getUser(@RequestParam int id) {
        User outUser = this.userService.getById(id);
        if (outUser == null) {
            throw new NotFoundException("User with id not found - " + id);
        }
        return userMapper.toDto(outUser);
    }

    @GetMapping(path = "/me")
    public UserSecurityDTO getMyUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getByEmail(auth.getName());
        return userSecurityMapper.toDto(user);
    }

    @PutMapping
    UserDTO updateUser(@RequestBody UserSecurityDTO userSecurityDTO) {
        User newUser = userService.saveUser(userSecurityMapper.toUser(userSecurityDTO));
        return userMapper.toDto(newUser);
    }

    @DeleteMapping(path = "/me")
    ResponseEntity<?>  deleteMyUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.getByEmail(email);
        userService.deleteUser(user.getId());
        if(userService.getByEmail(email)!=null)
            return ResponseEntity.ok(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "User wasn't deleted", System.currentTimeMillis()));
        return ResponseEntity.ok("User was deleted");
    }
}
