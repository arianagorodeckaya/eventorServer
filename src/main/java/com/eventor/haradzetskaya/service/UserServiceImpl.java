package com.eventor.haradzetskaya.service;

import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.enums.Role;
import com.eventor.haradzetskaya.mapper.UserMapper;
import com.eventor.haradzetskaya.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EventService eventService;
    @Autowired
    UserMapper userMapper;

    @Override
    public Page<User> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 20);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User getByEmail(String login) {
        return this.userRepository.findByEmail(login);
    }

    @Override
    public User getById(int id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        User user = null;
        if (userOptional.isPresent())
            user = userOptional.get();
        else
            throw new RuntimeException("User with id = " + user.getId() + " not found");
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = getByEmail(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getEmail(), u.getPwHash(), getGrantedAuthority(u));
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
        if(user.getId()!=0){
            Optional<User> userOptional = userRepository.findById(user.getId());
            if(!userOptional.isPresent()){
                throw new RuntimeException("User with id = "+user.getId()+" not found");
            }
            User oldUser = userOptional.get();
            if (!passwordEncoder.matches(oldUser.getPwHash(), user.getPwHash()))
                    user.setPwHash(passwordEncoder.encode(user.getPwHash()));
        }
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public Long getCountUsers() {
        return userRepository.count();
    }
}
