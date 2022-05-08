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
    @NonNull
    private String discription;

    @Column(name = "image")
    @NonNull
    private String image;

    @Column(name = "location", nullable = false)
    @NonNull
    private float location;

    @Column(name = "price", nullable = false)
    @NonNull
    private float price;

    @Column(name = "date_time", nullable = false)
    @NonNull
    private Date date;

    //private float duration;

    @Column(name = "status", nullable = false)
    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "archive", nullable = false)
    @NonNull
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

    public Event(@NonNull String discription, @NonNull float location, @NonNull float price, @NonNull Date date,  @NonNull Status status, @NonNull boolean archive, boolean confirmation, User creator) {
        this.discription = discription;
        this.location = location;
        this.price = price;
        this.date = date;
        this.status = status;
        this.archive = archive;
        this.confirmation = confirmation;
        this.creator = creator;
    }
}
