package org.edu.main.repository;

import org.edu.main.model.Role_Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<Role_Permission, Long> {
}
