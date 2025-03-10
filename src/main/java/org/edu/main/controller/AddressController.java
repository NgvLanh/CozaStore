package org.edu.main.controller;

import org.edu.main.dto.request.address.AddressRequest;
import org.edu.main.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    private ResponseEntity<?> CREATE(@Valid @RequestBody AddressRequest request) {
        return addressService.CREATE(request);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<?> UPDATE(@PathVariable long id, @RequestBody AddressRequest request) {
        return addressService.UPDATE(id, request);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> GET(@PathVariable long id) {
        return addressService.GET(id);
    }

}
