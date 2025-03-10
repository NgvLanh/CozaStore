package org.edu.main.controller;

import lombok.AllArgsConstructor;
import org.edu.main.service.BannerService;
import org.edu.main.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    private ResponseEntity<?> GETS() {
        return categoryService.GETS();
    }

}
