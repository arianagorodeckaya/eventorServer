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
    @NonNull
    private String name;

    @Column(name = "surname", nullable = false)
    @NonNull
    private String surname;

    @Column(name = "email", nullable = false)
    @NonNull
    private String email;

    @Column(name = "phone", nullable = false)
    @NonNull
    private String phone;

    @Column(name = "work")
    private String work;

    @Column(name = "birthday")
    private String birthday;


    @Column(name = "photo")
    private String photo;

    @Column(name = "role", nullable = false)
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "pw_hash", nullable = false)
    @NonNull
    private String pw_hash;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> creatorEvents;

    @ManyToMany(mappedBy = "users")
    private List<Event> events;

    public User() {
    }

    public User(@NonNull String name, @NonNull String surname, @NonNull String email, @NonNull String phone, String work, String birthday, @NonNull Role role, @NonNull String pw_hash) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.work = work;
        this.birthday = birthday;
        this.role = role;
        this.pw_hash = pw_hash;
    }
    public User(@NonNull String name, @NonNull String surname, @NonNull String email, @NonNull String phone, String work, String birthday,  @NonNull String pw_hash) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.work = work;
        this.birthday = birthday;
        this.pw_hash = pw_hash;
    }
}
