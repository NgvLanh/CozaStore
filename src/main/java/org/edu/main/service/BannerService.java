package org.edu.main.service;

import lombok.RequiredArgsConstructor;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.repository.BannerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerService {
    private final BannerRepository bannerRepository;

    public ResponseEntity<?> GETS() {
        return ResponseEntity.ok(ApiResponse.SUCCESS(bannerRepository.findByActiveTrue()));
    }
}
