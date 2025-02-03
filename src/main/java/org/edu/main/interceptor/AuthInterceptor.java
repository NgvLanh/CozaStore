package org.edu.main.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.model.auth.User;
import org.edu.main.repository.auth.UserRepository;
import org.edu.main.repository.auth.UserRoleRepository;
import org.edu.main.service.auth.JwtService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "accessToken".equals(cookie.getName()))
                        .findFirst())
                .map(Cookie::getValue)
                .orElse(null);

        if (accessToken == null) {
            return redirectToLogin(response, "Please log in to continue!");
        }

        try {
            String username = jwtService.extractUsername(accessToken);
            log.info("Authenticated user: {}", username);

            Optional<User> user = userRepository.findByEmail(username);
            if (user.isPresent()) {
                boolean isAdmin = userRoleRepository.findByUserId(user.get().getId()).stream()
                        .anyMatch(role -> "ADMIN".equalsIgnoreCase(role.getRole().getName()));

                if (request.getRequestURI().startsWith("/admin") && !isAdmin) {
                    return redirectToLogin(response, "Please log in to continue!");
                }
            }
            return true;
        } catch (Exception e) {
            log.error("Invalid access token: {}", e.getMessage());
            return redirectToLogin(response, "Your session has expired. Please login again.");        }
    }

    private boolean redirectToLogin(HttpServletResponse response, String msg) throws IOException {
        response.sendRedirect("/login?msg=" + msg);
        return false;
    }
}
