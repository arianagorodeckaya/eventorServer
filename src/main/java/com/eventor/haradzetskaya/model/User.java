package com.eventor.haradzetskaya.model;

import com.eventor.haradzetskaya.myEnum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
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
    private String pw_hash;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> creatorEvents;

    @ManyToMany(mappedBy = "users")
    private List<Event> events;

    public User() {
    }

    public User(String name, String email, String phone, String work, String birthday, String photo, Role role, String pw_hash) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.work = work;
        this.birthday = birthday;
        this.photo = photo;
        this.role = role;
        this.pw_hash = pw_hash;
    }


}
