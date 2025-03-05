package org.edu.main.model.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.edu.main.model.auth.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String fullName;

    String phoneNumber;

    String details;

    String ward;
    String district;
    String province;

    double shippingFee;

    @Builder.Default
    boolean active = true;

    @Builder.Default
    boolean defaultAddress = false;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
