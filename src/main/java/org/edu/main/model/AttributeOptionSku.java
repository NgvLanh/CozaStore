package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "attribute_option_sku") //  (Liên kết biến thể với thuộc tính)
public class AttributeOptionSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    Sku sku;

    @ManyToOne
    @JoinColumn(name = "attribute_option_id")
    AttributeOption attributeOption;
}
