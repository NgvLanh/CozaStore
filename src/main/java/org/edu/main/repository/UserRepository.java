package org.edu.main.repository;

import org.edu.main.dto.response.UserResponse;
import org.edu.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

    @Query("""
            select new org.edu.main.dto.response.UserResponse(
            u.id,
            u.fullName,
            u.email,
            u.image,
            u.phoneNumber,
            r.name
            )
            from users u
            left join roles r on r.id = u.role.id
            where u.id = ?1 and u.active = true
            """)
    UserResponse findUser(long userId);

}
