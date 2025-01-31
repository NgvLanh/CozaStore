package org.edu.main.repository.auth;

import org.edu.main.model.auth.Role_Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<Role_Permission, Long> {
}
