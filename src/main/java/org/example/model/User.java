package org.example.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private String password;

    private byte[] image;

    @ManyToMany
    @JoinTable(name = "User_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    public static User findByUsername(String username) {
        return find("username", username).firstResult();
    }
}
