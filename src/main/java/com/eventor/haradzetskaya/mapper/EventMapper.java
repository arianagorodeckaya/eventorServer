package com.eventor.haradzetskaya.mapper;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.entity.User;
import com.eventor.haradzetskaya.model.EventDTO;
import com.eventor.haradzetskaya.model.UserSecurityDTO;
import com.eventor.haradzetskaya.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class EventMapper {
    @Autowired
    UserService userService;
        public EventDTO toDto(Event event) {
            int id = event.getId();
            String name = event.getName();
            String description = event.getDescription();
            String image = event.getImage();
            float longitude = event.getLongitude();
            float latitude = event.getLatitude();
            float price = event.getPrice();
            Date startDate =event.getStartDate();
            Date endDate = event.getEndDate();
            boolean archive = event.isArchive();
            Boolean confirmation =event.getConfirmation();
            List<Integer> users =event
                    .getUsers()
                    .stream()
                    .map(User::getId)
                    .collect(toList());
            Integer creator = event.getCreator().getId();

            return new EventDTO(id, name, description, image, longitude, latitude, price, startDate, endDate,
                    archive, confirmation, users, creator);
        }

    public Event toEvent(EventDTO eventDTO) {
        List<User> users = new ArrayList<>();
        for (Integer id: eventDTO.getUsers()) {
            User user = userService.getById(id);
            users.add(user);
        }
        User creator = userService.getById(eventDTO.getCreator());

        return new Event(eventDTO.getId(), eventDTO.getName(), eventDTO.getDescription(),eventDTO.getImage(),
                eventDTO.getLongitude(),eventDTO.getLatitude(), eventDTO.getPrice(),eventDTO.getStartDate(),
                eventDTO.getEndDate(),eventDTO.isArchive(),eventDTO.getConfirmation(), users, creator);
    }
}
