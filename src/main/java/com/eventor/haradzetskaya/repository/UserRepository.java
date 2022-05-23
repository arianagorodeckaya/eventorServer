package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.User;

import java.util.List;

public interface UserRepository {

    public User getByEmail(String login);
    public User getById(int id);
    public List<User> getAll();
    public User saveUser(User user);
    public void deleteUser(int id);
}
