package org.edu.main.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResponse {
    long id;
    String name;
    double minPrice;
    double maxPrice;
    String filePath;
    String category;
}
