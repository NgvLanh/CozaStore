package org.edu.main.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.model.Role;
import org.edu.main.model.User;
import org.edu.main.repository.RoleRepository;
import org.edu.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppSetupConfig {

    private final String[] DEFAULT_ROLES = {"admin", "client"};

    @Value("${config.admin.username}")
    public String ADMIN_USERNAME;

    @Value("${config.admin.password}")
    public String ADMIN_PASSWORD;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            createDefaultRoles();
            createDefaultAdminAccount();
        };
    }

    private void createDefaultRoles() {
        for (String roleName : DEFAULT_ROLES) {
            roleRepository.findRoleByName(roleName).orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));
        }
        log.info("*** Default roles created: {}", Arrays.toString(DEFAULT_ROLES));
    }

    private void createDefaultAdminAccount() {
        Role role = roleRepository.findRoleByName(DEFAULT_ROLES[0]).orElse(null);
        userRepository.findByEmail(ADMIN_USERNAME)
                .orElseGet(() -> userRepository
                        .save(User.builder().email(ADMIN_USERNAME)
                                .password(encoder.encode(ADMIN_PASSWORD)).role(role).build()));
        log.info("*** Default Admin account created: Username: {} ", ADMIN_USERNAME);
    }
}
