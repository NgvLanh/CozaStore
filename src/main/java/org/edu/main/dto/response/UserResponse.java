package org.edu.main.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.edu.main.model.Address;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    long id;
    @JsonIgnore
    String password;
    String fullName;
    String email;
    String image;
    String phoneNumber;
    List<RoleResponse> roles;
    List<Address> addresses;
}
