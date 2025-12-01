package com._dawb.AUT03_02_Diccionario.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Este campo no puede estar en blanco.")
    @NotNull
    @Size(min = 3, message = "El usuario ha de tener al menos 3 caracteres.")
    @Column(unique = true)
    private String nickName;

    @NotBlank(message = "Este campo no puede estar en blanco.")
    @NotNull
    private String country;

    @NotBlank(message = "Este campo no puede estar en blanco.")
    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @NotBlank(message = "Este campo no puede estar en blanco.")
    @NotNull
    @Size(min = 5, message = "La clave ha de tener al menos 5 caracteres.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "roles_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role){
        roles.add(role);
    }

    public User() {}

    public User(String name, String country, String email, String password, Role role) {
        this.nickName = name;
        this.country = country;
        this.email = email;
        this.password = password;
        this.roles.add(role);
    }
}