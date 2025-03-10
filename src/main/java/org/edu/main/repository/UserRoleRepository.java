package org.edu.main.repository;

import org.edu.main.model.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<User_Role, Long> {
    List<User_Role> findByUserId(long userId);

    boolean existsByUserIdAndRoleId(long userId, long roleId);
}
