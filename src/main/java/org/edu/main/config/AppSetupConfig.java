package org.edu.main.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.model.auth.Role;
import org.edu.main.model.auth.User;
import org.edu.main.model.auth.User_Role;
import org.edu.main.repository.auth.RoleRepository;
import org.edu.main.repository.auth.UserRepository;
import org.edu.main.repository.auth.UserRoleRepository;
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
    private final UserRoleRepository userRoleRepository;

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            createDefaultRoles();
            createDefaultAdminAccount();
        };
    }

    private void createDefaultRoles() {
        for (String roleName : DEFAULT_ROLES) {
            roleRepository.findRoleByName(roleName)
                    .orElseGet(() -> roleRepository.save(Role.builder().name(roleName).build()));
        }
        log.info("*** Default roles created: {}", Arrays.toString(DEFAULT_ROLES));
    }

    private void createDefaultAdminAccount() {
        User user = userRepository.findByUsername(ADMIN_USERNAME)
                .orElseGet(() -> userRepository.save(User.builder()
                        .username(ADMIN_USERNAME)
                        .password(encoder.encode(ADMIN_PASSWORD))
                        .build()));
        updateAdminRole(user);
        log.info("*** Default Admin account created: Username: {} ", ADMIN_USERNAME);
    }

    private void updateAdminRole(User user) {
        Role role = roleRepository.findRoleByName(DEFAULT_ROLES[0])
                .orElseGet(() -> roleRepository.save(Role.builder().name(DEFAULT_ROLES[0]).build()));
        if (!userRoleRepository.existsByUserIdAndRoleId(user.getId(), role.getId())) {
            userRoleRepository.save(User_Role.builder().user(user).role(role).build());
        }

        log.info("*** Admin role assigned to: {}", user.getUsername());
    }
}
