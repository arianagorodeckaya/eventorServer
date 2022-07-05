package com.eventor.haradzetskaya.mapper;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.enums.Role;
import com.eventor.haradzetskaya.model.UserDTO;
import com.eventor.haradzetskaya.model.UserSecurityDTO;
import com.eventor.haradzetskaya.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserSecurityMapper {
    @Autowired
    EventService eventService;
    public UserSecurityDTO toDto(User user) {
        Integer id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String work = user.getWork();
        String birthday = user.getBirthday();
        String photo = user.getPhoto();
        Role role = user.getRole();
        String pwHash = user.getPwHash();
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

        return new UserSecurityDTO(id, name, email, phone, work, birthday, photo, role, pwHash, creatorEvents, events);
    }

    public User toUser(UserSecurityDTO userSecurityDTO) {
        List<Event> creatorEvents = new ArrayList<>();
        for (Integer idCreator: userSecurityDTO.getCreatorEvents()) {
            Event event = eventService.getById(idCreator);
            creatorEvents.add(event);
        }
        List<Event> events = new ArrayList<>();
        for (Integer id: userSecurityDTO.getEvents()) {
            Event event = eventService.getById(id);
            events.add(event);
        }
        return new User(userSecurityDTO.getId(), userSecurityDTO.getName(), userSecurityDTO.getEmail(),userSecurityDTO.getPhone(), userSecurityDTO.getWork(),userSecurityDTO.getBirthday(),
                userSecurityDTO.getPhoto(),userSecurityDTO.getRole(), userSecurityDTO.getPwHash(),creatorEvents, events);
    }
}
