package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Random;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "skus") //  (Thông tin thêm sản phẩm)
public class Sku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    double price;

    String code;

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
