package org.edu.main.controller;

import lombok.AllArgsConstructor;
import org.edu.main.service.BannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/banners")
public class BannerController {

    private final BannerService bannerService;

    @GetMapping
    private ResponseEntity<?> GETS() {
        return bannerService.GETS();
    }

}
