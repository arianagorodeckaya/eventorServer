package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.myEnum.Role;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(path = "/users")
    public  @ResponseBody
    List<User> getAll() {
        List<User> users = this.userService.getAll();
        for (User user: users) {
            System.out.println("Before "+ user.getPw_hash());
            user.setPw_hash(passwordEncoder.encode(user.getPw_hash()));
            System.out.println("After "+ user.getPw_hash());
            user.setCreatorEvents(userService.setOnlyIdForUser(user));
        }
        return users;
    }

    @DeleteMapping(path = "/user")
    void deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
    }

    @PostMapping(path = "/save")
    User saveUser(@RequestBody User newUser) {
        newUser.setRole(Role.ADMIN);
        newUser.setId(0);
        newUser.setPw_hash(passwordEncoder.encode(newUser.getPw_hash()));
        return userService.saveUser(newUser);
    }
}
