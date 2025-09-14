package com.parking.entity;

import com.parking.enums.Role;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class EUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
}
