package org.edu.main.service.address;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.dto.request.address.AddressRequest;
import org.edu.main.dto.request.auth.LoginRequest;
import org.edu.main.dto.request.auth.RegisterRequest;
import org.edu.main.dto.request.user.UserRequest;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.dto.response.auth.AuthResponse;
import org.edu.main.dto.response.auth.RoleResponse;
import org.edu.main.dto.response.auth.UserResponse;
import org.edu.main.model.address.Address;
import org.edu.main.model.auth.Role;
import org.edu.main.model.auth.User;
import org.edu.main.model.auth.User_Role;
import org.edu.main.repository.address.AddressRepository;
import org.edu.main.repository.auth.RoleRepository;
import org.edu.main.repository.auth.UserRepository;
import org.edu.main.repository.auth.UserRoleRepository;
import org.edu.main.service.auth.JwtService;
import org.edu.main.util.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public ResponseEntity<?> CREATE(AddressRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getUserId());

        Address address = Address.builder()
                .user(userOpt.orElse(null))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .details(request.getDetails())
                .ward(request.getWard())
                .district(request.getDistrict())
                .province(request.getProvince())
                .shippingFee(request.getShippingFee())
                .build();

        addressRepository.save(address);
        return ResponseEntity.ok(ApiResponse.SUCCESS(address));
    }

    public ResponseEntity<?> UPDATE(long id, @Valid AddressRequest request) {
        return ResponseEntity.ok(ApiResponse.ERROR());
    }
}
