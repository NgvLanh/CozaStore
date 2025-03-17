package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "attribute_option_sku") //  (Liên kết biến thể với thuộc tính)
public class AttributeOptionSku extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "sku_id")
    Sku sku;

    @ManyToOne
    @JoinColumn(name = "attribute_option_id")
    AttributeOption attributeOption;
}
