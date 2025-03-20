package org.edu.main.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequest {
    @NotNull
    Long skuId;
    @NotNull
    Integer quantity;
}
