package org.edu.main.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.edu.main.service.auth.JwtService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
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

}
