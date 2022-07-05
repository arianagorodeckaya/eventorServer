package com.eventor.haradzetskaya.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image")
    private String image;

    //долгота
    @Column(name = "longitude", nullable = false)
    private float longitude;

    //широта
    @Column(name = "latitude", nullable = false)
    private float latitude;

    @Column(name = "price", nullable = false)
    @NonNull
    private float price;

    @Column(name = "start_date_time", nullable = false)
    private Date startDate;

    @Column(name = "end_date_time", nullable = false)
    private Date endDate;

    @Column(name = "archive", nullable = false)
    private boolean archive;

    @Column(name = "confirmation")
    private Boolean confirmation;

    @ManyToMany(mappedBy = "events", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private List<User> users;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "creator_id")
    @JsonIgnore
    private User creator;

    public void addUser(User user){

        if(users == null){
            users = new ArrayList<>();
        }
        users.add(user);
    }
}