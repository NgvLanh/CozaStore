package org.edu.main.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRequest {

    @NotBlank(message = "Full name cannot be empty")
    @Length(min = 2, max = 50, message = "Full name length must be 2 -> 50 characters")
    String fullName;

    @NotBlank(message = "Phone number cannot be empty")
    String phoneNumber;

    String base64Image;

    String fileName;

}
