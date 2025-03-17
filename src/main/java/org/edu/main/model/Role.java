package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "roles")
public class Role extends BaseModel{

    @Column(unique = true)
    String name;

    @OneToMany(mappedBy = "role")
    List<Role_Permission> permissions;

}
