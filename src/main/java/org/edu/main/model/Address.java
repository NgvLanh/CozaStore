package org.edu.main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "addresses")
public class Address extends BaseModel {

    String fullName;

    String phoneNumber;

    String details;

    String ward;
    String district;
    String province;

    Double shippingFee;

    @Builder.Default
    Boolean defaultAddress = false;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

}
