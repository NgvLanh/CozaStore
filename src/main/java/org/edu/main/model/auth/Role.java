package org.edu.main.model.auth;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;


    @Column(unique = true)
    String name;

    @OneToMany(mappedBy = "role")
    List<Role_Permission> permissions;

    public String getName() {
        return name.toUpperCase();
    }
}
