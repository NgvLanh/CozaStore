package org.edu.main.dto.request.address;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddressRequest {

    @NotNull(message = "User ID is required")
    long userId;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name should not exceed 100 characters")
    String fullName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    String phoneNumber;

    @NotBlank(message = "Address details are required")
    String details;

    @NotBlank(message = "Ward is required")
    String ward;

    @NotBlank(message = "District is required")
    String district;

    @NotBlank(message = "Province is required")
    String province;

    Boolean defaultAddress;

    @NotNull(message = "Shipping fee is required")
    @Min(value = 0, message = "Shipping fee must be positive")
    double shippingFee;
}
