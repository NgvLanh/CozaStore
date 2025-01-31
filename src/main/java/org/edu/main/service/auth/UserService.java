package org.edu.main.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.dto.response.auth.RoleResponse;
import org.edu.main.dto.response.auth.UserResponse;
import org.edu.main.model.auth.Role;
import org.edu.main.model.auth.Role_Permission;
import org.edu.main.model.auth.User;
import org.edu.main.model.auth.User_Role;
import org.edu.main.repository.auth.RolePermissionRepository;
import org.edu.main.repository.auth.UserRepository;
import org.edu.main.repository.auth.UserRoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public ResponseEntity<?> GETS() {
        List<User> users = userRepository.findAll();
        List<User_Role> userRoles = userRoleRepository.findAll();
        List<Role_Permission> rolePermissions = rolePermissionRepository.findAll();

        List<UserResponse> userResponses = users.stream()
                .map(user -> {
                    // Lấy danh sách role của user hiện tại
                    List<RoleResponse> roleResponses = userRoles.stream()
                            .filter(userRole -> userRole.getUser().getId() == user.getId())
                            .map(userRole -> {
                                Role role = userRole.getRole();

                                // Lấy danh sách permission của role hiện tại
                                List<String> permissions = rolePermissions.stream()
                                        .filter(rolePermission -> rolePermission.getRole().getId() == role.getId())
                                        .map(rolePermission -> rolePermission.getPermission().getName())
                                        .collect(Collectors.toList());

                                // Tạo RoleResponse với danh sách permissions
                                return new RoleResponse(role.getId(), role.getName(), permissions);
                            })
                            .collect(Collectors.toList());

                    // Tạo UserResponse với danh sách roleResponses
                    return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getFullName(), user.getEmail(), user.getImage(), roleResponses);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.SUCCESS(userResponses));
    }

    public ResponseEntity<?> GET(long id) {
        return null;
    }

    public ResponseEntity<?> UPDATE(long id) {
        return null;
    }

    public ResponseEntity<?> DELETE(long id) {
        return null;
    }

}
