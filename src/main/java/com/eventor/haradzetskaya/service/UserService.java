package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public Page<User> getAll(int page);
    public User getById(int id);
    public User saveUser(User user);
    public User getByEmail(String email);
    public void deleteUser(int id);
    public Long getCountUsers();
}
