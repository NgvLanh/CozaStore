package org.edu.main.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "role_permissions")
public class Role_Permission extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "permission_id")
    Permission permission;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}
