package org.edu.main.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "users")
public class User extends BaseModel {

    @Column(unique = true)
    String email;
    String fullName;
    String image;
    String password;
    String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}
