package com.buynsell.buynsell.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "USERS")
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(max = 15)
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    @NotNull
    private boolean isActive;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Item> items;

    public User() {
    }

    public User(String name, String username, String email, String password, boolean isActive, Set<Item> items) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.items = items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addItem(Item item) {
        if (items == null)
            items = new HashSet<>();
        items.add(item);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
