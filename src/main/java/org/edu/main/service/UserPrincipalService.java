package org.edu.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.model.User;
import org.edu.main.model.UserPrincipal;
import org.edu.main.model.User_Role;
import org.edu.main.repository.UserRepository;
import org.edu.main.repository.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            log.info("User Not Found!");
            throw new UsernameNotFoundException("User Not Found!");
        }
        List<User_Role> userRoles = userRoleRepository.findByUserId(user.get().getId());

        return new UserPrincipal(user.get(), userRoles);
    }
}
