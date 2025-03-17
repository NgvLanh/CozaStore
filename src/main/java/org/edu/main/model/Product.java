package org.edu.main.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.text.Normalizer;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "products") // (Sản phẩm gốc)
public class Product extends BaseModel {

    @Column(unique = true)
    String name;
    String slug;
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    public void setSlug() {
        String normalized = Normalizer.normalize(this.name, Normalizer.Form.NFD);
        this.slug = normalized.replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
