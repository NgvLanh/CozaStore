package org.edu.main.service.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.dto.request.user.UserRequest;
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
import org.edu.main.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final Utils utils;

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
                    return new UserResponse(user.getId(), user.getPassword(), user.getFullName(),
                            user.getEmail(), user.getImage(), user.getPhoneNumber(), roleResponses, null);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.SUCCESS(userResponses));
    }

    public ResponseEntity<?> GET(long id) {
        return null;
    }

    public ResponseEntity<?> UPDATE(long id, UserRequest request) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFullName(request.getFullName());
                    user.setPhoneNumber(request.getPhoneNumber());
                    if (request.getBase64Image() != null) {
                        try {
                            String imageUrl = utils.base64ToFileName(request.getBase64Image(), request.getFileName());
                            user.setImage(imageUrl);
                        } catch (IOException e) {
                            return ResponseEntity.ok(ApiResponse.ERROR(Map.of("system", "File upload error!")));
                        }
                    }
                    userRepository.save(user);
                    return ResponseEntity.ok(ApiResponse.SUCCESS(user));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.ERROR(Map.of("system", "User not found!"))));
    }


    public ResponseEntity<?> DELETE(long id) {
        return null;
    }

    public ResponseEntity<?> PROFILE(HttpServletRequest request) {
        try {
            String accessToken = request.getHeader("cookie").split("=")[1];
            String username = jwtService.extractUsername(accessToken);
            if (username != null) {
                Optional<User> user = userRepository.findByEmail(username);
                if (user.isPresent()) {
                    UserResponse userResponse = UserResponse.builder()
                            .fullName(user.get().getFullName())
                            .email(user.get().getEmail())
                            .phoneNumber(user.get().getPhoneNumber())
                            .image(user.get().getImage())
                            .build();
                    return ResponseEntity.ok(ApiResponse.SUCCESS(userResponse));
                }
            }
            return ResponseEntity.ok(ApiResponse.ERROR(Map.of("system", "User not found!")));
        } catch (Exception e) {
            log.info("Token is expired!");
            return ResponseEntity.ok(ApiResponse.ERROR(Map.of("system", "Token is expired!")));
        }
    }
}
