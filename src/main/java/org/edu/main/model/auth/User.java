package org.edu.main.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.edu.main.model.address.Address;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(unique = true)
    String email;

    String fullName;

    String image;

    String password;

    String phoneNumber;

    @Builder.Default
    boolean active = true;

    @OneToMany(mappedBy = "user")
    List<User_Role> roles;

    @OneToMany(mappedBy = "user")
    List<Address> addresses;
}
