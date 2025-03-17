package org.edu.main.controller;

import lombok.RequiredArgsConstructor;
import org.edu.main.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    private ResponseEntity<?> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getProductDetails(@PathVariable Long id) {
        return productService.getProductDetails(id);
    }

}
