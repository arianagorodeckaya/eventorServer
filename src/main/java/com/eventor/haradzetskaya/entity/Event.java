package com.eventor.haradzetskaya.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
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

    @ManyToMany(mappedBy = "events")
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    public Event() {

    }
}