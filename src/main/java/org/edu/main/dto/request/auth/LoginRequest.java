package org.edu.main.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class LoginRequest {
    @NotBlank(message = "Username cannot be empty")
    @Length(min = 2, max = 50, message = "Username length must be 2 -> 50 characters")
    String username;

    @NotBlank(message = "Password cannot be empty")
    String password;
}
