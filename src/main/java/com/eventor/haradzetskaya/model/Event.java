package com.eventor.haradzetskaya.model;


import com.eventor.haradzetskaya.myEnum.Status;
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

    @Column(name = "discription", nullable = false)
    private String discription;

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

    @Column(name = "date_time", nullable = false)
    private Date date;

    //private float duration;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "archive", nullable = false)
    private boolean archive;

    @Column(name = "confirmation")
    private boolean confirmation;

    @ManyToMany
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    public Event() {

    }
    }