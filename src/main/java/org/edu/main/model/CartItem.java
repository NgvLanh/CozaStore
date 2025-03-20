package org.edu.main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "cart_items")
public class CartItem extends BaseModel {
    Integer quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @JoinColumn(name = "sku_id")
    Sku sku;
}
