package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.model.Event;
import com.eventor.haradzetskaya.model.User;
import com.eventor.haradzetskaya.myEnum.Role;
import com.eventor.haradzetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return this.userRepository.getAll();
    }

    @Override
    public User getByEmail(String login) {
        return this.userRepository.getByEmail(login);
    }


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = getByEmail(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPw_hash(), getGrantedAuthority(u));
    }

    private Collection<GrantedAuthority> getGrantedAuthority(User user){
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(user.getRole().equals(Role.ADMIN))
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setPw_hash(passwordEncoder.encode(user.getPw_hash()));
        return userRepository.saveUser(user);
    }

    @Override
    public User updateUser(User user) {
        User oldUser = userRepository.getById(user.getId());
            if (oldUser.getPw_hash().equals(passwordEncoder.encode(user.getPw_hash()))) {
                user.setPw_hash(passwordEncoder.encode(user.getPw_hash()));
        }
        return userRepository.updateUser(user);
    }

    @Override
    public User saveAdmin(User user) {
        user.setRole(Role.ADMIN);
        user.setPw_hash(passwordEncoder.encode(user.getPw_hash()));
        return userRepository.saveUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Override
    public User getByLoginAndPassword(String email, String password) {
        User user = getByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPw_hash())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<Event> setOnlyIdForUser(User user) {
        List<Event> events = user.getCreatorEvents();
        List<Event> newEvents = new ArrayList<>();
        for (Event event: events){
            int id = event.getCreator().getId();
            User newUser = new User();
            newUser.setId(id);
            event.setCreator(newUser);
            newEvents.add(event);
        }
        return newEvents;
    }

}
