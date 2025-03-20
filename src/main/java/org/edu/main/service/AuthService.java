package org.edu.main.service;

import java.util.*;

import org.edu.main.dto.request.LoginRequest;
import org.edu.main.dto.request.RegisterRequest;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.dto.response.AuthResponse;
import org.edu.main.model.Cart;
import org.edu.main.model.Role;
import org.edu.main.model.User;
import org.edu.main.repository.CartRepository;
import org.edu.main.repository.RoleRepository;
import org.edu.main.repository.UserRepository;
import org.edu.main.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final Utils utils;
    private final CartRepository cartRepository;

    public ResponseEntity<?> REGISTER(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponse.ERROR(Map.of("email", "Email already exists")));
        }
        Role role = roleRepository.findById(request.getRoleId()).orElse(null);
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(encoder.encode(request.getPassword()))
                .image(request.getImage())
                .role(role)
                .build();
        Cart cart = Cart.builder().user(user).build();
        cartRepository.save(cart);
        User createdUser = userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.SUCCESS(createdUser));
    }

    public ResponseEntity<?> LOGIN(LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
                if (user.isPresent()) {
                    User userLogin = user.get();
                    String token = jwtService.generateToken(userLogin.getEmail(), 5);

                    Cookie cookie = new Cookie("accessToken", token);
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setPath("/");
                    cookie.setMaxAge(60 * 60);
                    response.addCookie(cookie);

                    return ResponseEntity.ok(ApiResponse
                            .SUCCESS(new AuthResponse(token, userRepository.findUser(userLogin.getId()))));
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
        if (isLogin) {
            try {
                String accessToken = utils.extractToken(request);
                String username = jwtService.extractUsername(accessToken);
                if (username != null) {
                    Optional<User> user = userRepository.findByEmail(username);
                    if (user.isPresent()) {
                        return ResponseEntity.ok(ApiResponse.SUCCESS(Map.of("fullName", user.get().getFullName())));
                    }
                }
                return ResponseEntity.ok(ApiResponse.ERROR(Map.of("system", "User not found!")));
            } catch (Exception e) {
                log.info(e.getMessage());
                return ResponseEntity.ok(ApiResponse.ERROR(Map.of("system", e.getMessage())));
            }
        }
        return ResponseEntity.badRequest().body(ApiResponse.ERROR(Map.of("System", "Session has expired")));
    }
}
