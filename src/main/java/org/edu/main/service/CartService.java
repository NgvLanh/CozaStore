package org.edu.main.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.edu.main.dto.request.CartRequest;
import org.edu.main.repository.CartItemRepository;
import org.edu.main.repository.CartRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    public ResponseEntity<?> addProductToCart(CartRequest cartRequest) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(authentication);
    }
}
