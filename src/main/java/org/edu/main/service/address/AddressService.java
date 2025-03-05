package org.edu.main.service.address;

import java.util.Optional;

import org.edu.main.dto.request.address.AddressRequest;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.model.address.Address;
import org.edu.main.model.auth.User;
import org.edu.main.repository.address.AddressRepository;
import org.edu.main.repository.auth.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        return ResponseEntity.ok(ApiResponse.SUCCESS("123"));
    }

    public ResponseEntity<?> GET(long id) {
        return ResponseEntity.ok(ApiResponse.SUCCESS(addressRepository.findAddressByActiveTrueAndUserId(id)));
    }

}
