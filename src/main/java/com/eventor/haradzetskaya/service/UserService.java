package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    public List<User> getAll();
    public User getByEmail(String email);
}
