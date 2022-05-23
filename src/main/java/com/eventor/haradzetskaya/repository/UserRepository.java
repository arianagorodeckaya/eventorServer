package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository {

    public User findByEmail(String login);
    public User findById(int id);
    public Page<User> findAll(Pageable pageable);
    public User saveUser(User user);
    public void deleteUser(int id);
}
