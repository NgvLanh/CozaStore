package org.edu.main.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;
import org.edu.main.service.JwtService;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class Utils {

    private final JwtService jwtService;
    private static final ResourceBundle bundle = ResourceBundle.getBundle("msg");


    public boolean accessTokenCheck(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    try {
                        jwtService.extractUsername(cookie.getValue());
                        return true;
                    } catch (Exception e) {
                        log.info("AccessToken Check {}", e.getMessage());
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public String extractToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    log.info("AccessToken Extract {}", cookie.getName());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String base64ToFileName(String base64, String originalFileName) throws IOException {

        byte[] decodedBytes = Base64.getDecoder().decode(base64);

        Path path = Paths.get("uploads/images");

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Path filePath = path.resolve(originalFileName);

        if (Files.exists(filePath)) {
            return "/uploads/images/" + originalFileName;
        }

        Files.write(filePath, decodedBytes);
        return "/uploads/images/" + originalFileName;
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
