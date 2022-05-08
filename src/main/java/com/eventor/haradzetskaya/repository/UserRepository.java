package com.eventor.haradzetskaya.repository;

import com.eventor.haradzetskaya.model.User;

import java.util.List;

public interface UserRepository {

    public User getByEmail(String login);
    public List<User> getAll();
}
