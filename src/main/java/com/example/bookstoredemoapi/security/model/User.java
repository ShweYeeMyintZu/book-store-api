package com.example.bookstoredemoapi.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    @Column(name = "username",unique = true)
    private String userName;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Transient
    private String token;

    public User() {
    }

    public User(String name, String userName, String password,Role role) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.role=role;
    }
}
