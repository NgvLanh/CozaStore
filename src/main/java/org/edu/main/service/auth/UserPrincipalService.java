package org.edu.main.service.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.model.auth.User;
import org.edu.main.model.auth.UserPrincipal;
import org.edu.main.model.auth.User_Role;
import org.edu.main.repository.auth.UserRepository;
import org.edu.main.repository.auth.UserRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).get();
        if (user == null) {
            log.info("User Not Found!");
            throw new UsernameNotFoundException("User Not Found!");
        }
        List<User_Role> userRoles = userRoleRepository.findByUserId(user.getId());

        return new UserPrincipal(user, userRoles);
    }
}
