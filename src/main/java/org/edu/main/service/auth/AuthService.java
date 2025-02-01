package org.edu.main.service.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.dto.request.auth.LoginRequest;
import org.edu.main.dto.request.auth.RegisterRequest;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.dto.response.auth.AuthResponse;
import org.edu.main.dto.response.auth.RoleResponse;
import org.edu.main.dto.response.auth.UserResponse;
import org.edu.main.model.auth.Role;
import org.edu.main.model.auth.User;
import org.edu.main.model.auth.User_Role;
import org.edu.main.repository.auth.RoleRepository;
import org.edu.main.repository.auth.UserRepository;
import org.edu.main.repository.auth.UserRoleRepository;
import org.edu.main.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final Utils utils;

    public ResponseEntity<?> REGISTER(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(ApiResponse.ERROR(Map.of("Username", "Username already exists")));
        } else if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponse.ERROR(Map.of("Email", "Email already exists")));
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .image(request.getImage())
                .build();

        User createdUser = userRepository.save(user);

        User_Role userRole = User_Role.builder()
                .user(createdUser)
                .role(roleRepository.findRoleByName("client").orElseGet(() ->
                        Role.builder().name("client").build()))
                .build();

        userRoleRepository.save(userRole);

        return ResponseEntity.ok(ApiResponse.SUCCESS(createdUser));
    }

    public ResponseEntity<?> LOGIN(LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
                if (user.isPresent()) {
                    User userLogin = user.get();
                    List<RoleResponse> roleResponses = new ArrayList<>();
                    userLogin.getRoles().forEach(userRole -> {
                        List<String> permissions = new ArrayList<>();
                        userRole.getRole().getPermissions().forEach(rolePermission -> {
                            permissions.add(rolePermission.getPermission().getName() != null
                                    ? rolePermission.getPermission().getName() : null);
                        });
                        roleResponses.add(new RoleResponse(userRole.getRole().getId(), userRole.getRole().getName(), permissions));
                    });

                    String token = jwtService.generateToken(userLogin.getUsername(), 5);

                    Cookie cookie = new Cookie("accessToken", token);
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setPath("/");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                    UserResponse userResponse =
                            new UserResponse(userLogin.getId(), userLogin.getUsername(),
                                    userLogin.getPassword(), userLogin.getFullName(), userLogin.getEmail(),
                                    userLogin.getImage(), userLogin.getPhoneNumber(), roleResponses);
                    return ResponseEntity.ok(ApiResponse.SUCCESS(
                            new AuthResponse(token, userResponse)));
                }
            }
        }
        return ResponseEntity.ok(ApiResponse.ERROR(Map.of("System", "Login failed")));
    }

    public void LOGOUT(HttpServletResponse response) {
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public ResponseEntity<?> VERIFY(HttpServletRequest request) {
        boolean isLogin = utils.accessTokenCheck(request);
        return isLogin ? ResponseEntity.ok(ApiResponse.SUCCESS(true)) :
                ResponseEntity.badRequest().body(ApiResponse.ERROR(Map.of("System", "Session has expired")));
    }
}
