package com.eventor.haradzetskaya.controller;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(path = "/user/all", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PostMapping(path = "/user/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    void deleteUser(@RequestBody User user) {
        userService.deleteUser(user.getId());
    }

    @PostMapping(path = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    User saveUser(@RequestBody User newUser) {
        return userService.saveAdmin(newUser);
    }
}
