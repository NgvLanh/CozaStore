package org.edu.main.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.main.dto.request.address.AddressRequest;
import org.edu.main.dto.request.user.UserRequest;
import org.edu.main.service.address.AddressService;
import org.edu.main.service.auth.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<?> UPDATE(@PathVariable long id, @Valid @RequestBody AddressRequest request) {
        return addressService.UPDATE(id, request);
    }

}
