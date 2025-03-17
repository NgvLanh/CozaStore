package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "attribute_options") //  (Giá trị thuộc tính)
public class AttributeOption extends BaseModel {

    @Column(unique = true)
    String value;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    Attribute attribute;
}
