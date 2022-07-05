package com.eventor.haradzetskaya.model;

import com.eventor.haradzetskaya.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private int id;
    private String name;
    private String description;
    private String image;
    private float longitude;
    private float latitude;
    private float price;
    private Date startDate;
    private Date endDate;
    private boolean archive;
    private Boolean confirmation;
    private List<Integer> users;
    private Integer creator;
}
