package org.edu.main.config;

import lombok.RequiredArgsConstructor;
import org.edu.main.interceptor.AuthInterceptor;
import org.edu.main.repository.auth.UserRepository;
import org.edu.main.repository.auth.UserRoleRepository;
import org.edu.main.service.auth.JwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwtService, userRepository, userRoleRepository))
                .addPathPatterns("/admin/**")
                .addPathPatterns("/profile/**")
                .excludePathPatterns("/login", "/register");
    }
}
