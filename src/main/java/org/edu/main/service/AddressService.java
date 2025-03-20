package org.edu.main.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.edu.main.dto.request.AddressRequest;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.model.Address;
import org.edu.main.model.User;
import org.edu.main.repository.AddressRepository;
import org.edu.main.repository.UserRepository;
import org.edu.main.util.Utils;
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
    private final Utils utils;

    public ResponseEntity<?> CREATE(AddressRequest request) {
        int addressLength = addressRepository.findAddressByActiveTrueAndUserId(request.getUserId()).size();
        if (addressLength == 5) {
            return ResponseEntity
                    .badRequest().body(ApiResponse.ERROR(Map.of(utils.getMessage("key.system"),
                            utils.getMessage("msg.error.limit-address"))));
        }
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
        if (addressLength == 0) {
            address.setDefaultAddress(true);
        }
        addressRepository.save(address);
        return ResponseEntity.ok(ApiResponse.SUCCESS(address));
    }

    public ResponseEntity<?> UPDATE(long id, @Valid AddressRequest request) {
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            return ResponseEntity.badRequest().body(ApiResponse
                    .ERROR(Map.of(utils.getMessage("key.system"), utils.getMessage("msg.error.notfound"))));
        }
        List<Address> addresses = addressRepository.findAddressByActiveTrueAndUserId(request.getUserId());
        addresses.forEach(a -> {
            if (a.getId() != id) {
                a.setDefaultAddress(false);
            }
        });
        addressRepository.saveAll(addresses);

        address.setFullName(request.getFullName() != null ? request.getFullName() : address.getFullName());
        address.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber() : address.getPhoneNumber());
        address.setDetails(request.getDetails() != null ? request.getDetails() : address.getDetails());
        address.setWard(request.getWard() != null ? request.getWard() : address.getWard());
        address.setDistrict(request.getDistrict() != null ? request.getDistrict() : address.getDistrict());
        address.setProvince(request.getProvince() != null ? request.getProvince() : address.getProvince());
        address.setDefaultAddress(request.getDefaultAddress() != null ? request.getDefaultAddress() : address.getDefaultAddress());
        address.setShippingFee(request.getShippingFee() >= 0 ? request.getShippingFee() : address.getShippingFee());

        addressRepository.save(address);
        return ResponseEntity.ok(ApiResponse.SUCCESS(address));
    }


    public ResponseEntity<?> GET(long id) {
        return ResponseEntity.ok(ApiResponse.SUCCESS(addressRepository.findAddressByActiveTrueAndUserId(id)));
    }

}
