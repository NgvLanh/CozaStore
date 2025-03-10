package org.edu.main.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.dto.response.ProductResponse;
import org.edu.main.model.Category;
import org.edu.main.model.Image;
import org.edu.main.model.Product;
import org.edu.main.model.Sku;
import org.edu.main.repository.CategoryRepository;
import org.edu.main.repository.ImageRepository;
import org.edu.main.repository.ProductRepository;
import org.edu.main.repository.SkuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<?> GETS(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.SUCCESS(productRepository.getProducts(pageable)));
    }

}
