package org.edu.main.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.edu.main.service.auth.JwtService;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Utils {

    private final JwtService jwtService;

    public boolean accessTokenCheck(HttpServletRequest request) {
        String accessToken = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                        .filter(cookie -> "accessToken".equals(cookie.getName()))
                        .findFirst())
                .map(Cookie::getValue)
                .orElse(null);
        try {
            jwtService.extractUsername(accessToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
