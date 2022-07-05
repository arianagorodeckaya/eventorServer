package com.eventor.haradzetskaya.model;

import com.eventor.haradzetskaya.entity.Event;
import com.eventor.haradzetskaya.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String work;
    private String birthday;
    private String photo;
    private Role role;
    private List<Integer> creatorEvents;
    private List<Integer> events;
}