package org.edu.main.config;

import lombok.RequiredArgsConstructor;
import org.edu.main.interceptor.AuthInterceptor;
import org.edu.main.repository.UserRepository;
import org.edu.main.repository.UserRoleRepository;
import org.edu.main.service.JwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final String[] URL = {
            // api
            "/api/users/**",

            // route
            "/admin/**",
            "/profile/**"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(jwtService, userRepository, userRoleRepository))
                .addPathPatterns(URL)
                .excludePathPatterns("/login", "/register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:uploads/images/");
    }
}
