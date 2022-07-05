package com.eventor.haradzetskaya.entity;

import com.eventor.haradzetskaya.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "work")
    private String work;

    @Column(name = "birthday")
    private String birthday;


    @Column(name = "photo")
    private String photo;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "pw_hash", nullable = false)
    private String pwHash;

    @OneToMany(mappedBy = "creator", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    private List<Event> creatorEvents;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonIgnore
    @JoinTable(
            name = "user_event",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private List<Event> events;

    public User(String name, String email, String phone, String work, String birthday, String photo, Role role, String pwHash) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.work = work;
        this.birthday = birthday;
        this.photo = photo;
        this.role = role;
        this.pwHash = pwHash;
    }

    public void addCreatorEvent(Event event){

        if(events == null){
            events = new ArrayList<>();
        }
        events.add(event);
        event.setCreator(this);
    }

    public void addEvents(Event event){

        if(events == null){
            events = new ArrayList<>();
        }
        events.add(event);
    }

}
