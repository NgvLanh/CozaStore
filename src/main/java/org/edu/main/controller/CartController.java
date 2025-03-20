package org.edu.main.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.main.dto.request.AddressRequest;
import org.edu.main.dto.request.CartRequest;
import org.edu.main.service.AddressService;
import org.edu.main.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    private ResponseEntity<?> addToCart(@Valid @RequestBody CartRequest request) {
        return cartService.addProductToCart(request);
    }
}
