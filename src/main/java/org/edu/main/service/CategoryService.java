package org.edu.main.service;

import lombok.RequiredArgsConstructor;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.repository.BannerRepository;
import org.edu.main.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<?> GETS() {
        return ResponseEntity.ok(ApiResponse.SUCCESS(categoryRepository.findByActiveTrue()));
    }
}
