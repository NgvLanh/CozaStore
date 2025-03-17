package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.Random;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "skus") //  (Thông tin thêm sản phẩm)
public class Sku extends BaseModel {

    double price;
    String code;
    @Builder.Default
    int stock = 0;

    public void setCode() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        this.code = sb.toString();
    }

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
