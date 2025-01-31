package org.edu.main.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username cannot be empty")
    @Length(min = 2, max = 50, message = "Username length must be 2 -> 50 characters")
    String username;

    @NotBlank(message = "Password cannot be empty")
    String password;

    @NotBlank(message = "Full name cannot be empty")
    String fullName;

    @Pattern(regexp = "^(.+)\\.(jpg|jpeg|png|gif)$", message = "Image must have a valid format (jpg, jpeg, png, gif)")
    @Size(max = 255, message = "Image path must be less than 255 characters")
    String image;

    @Email(message = "Email must be valid")
    String email;

    Long roleId = null;
}
