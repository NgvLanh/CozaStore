package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "attributes") // (Thuộc tính biến thể)
public class Attribute extends BaseModel {

    @Column(unique = true)
    String name;
}
