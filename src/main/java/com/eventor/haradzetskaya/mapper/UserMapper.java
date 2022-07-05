package com.eventor.haradzetskaya.mapper;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.enums.Role;
import com.eventor.haradzetskaya.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {
        Integer id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String work = user.getWork();
        String birthday = user.getBirthday();
        String photo = user.getPhoto();
        Role role = user.getRole();
        List<Integer> creatorEvents = user
                .getCreatorEvents()
                .stream()
                .map(Event::getId)
                .collect(toList());
        List<Integer> events = user
                .getEvents()
                .stream()
                .map(Event::getId)
                .collect(toList());

        return new UserDTO(id, name, email, phone, work, birthday, photo, role, creatorEvents, events);
    }
//
//    public User toUser(UserDTO userDTO) {
//        return new User(userDTO.getId(), userDTO.getName(), userDTO.getEmail(),userDTO.getPhone(), userDTO.getWork(),userDTO.getBirthday(),
//                userDTO.getPhoto(),userDTO.getRole(), userDTO.getPwHash(), userDTO.getCreatorEvents(), userDTO.getEvents());
//    }
}
