package org.edu.main.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.edu.main.dto.response.ApiResponse;
import org.edu.main.dto.response.ProductDetailsResponse;
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

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ResponseEntity<?> getProducts(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.SUCCESS(productRepository.getProducts(pageable)));
    }

    public ResponseEntity<?> getProductDetails(Long id) {
        Object productDetails = productRepository.getProductDetails(id);
        if (productDetails != null) {
            Object[] row = (Object[]) productDetails;
            Map<String, Set<String>> attributesMap = parseAttributes((String) row[6]);
            System.out.println(attributesMap);
            return ResponseEntity.ok(ApiResponse.SUCCESS(
                    new ProductDetailsResponse(
                            (String) row[1],
                            ((Number) row[2]).doubleValue(),
                            ((Number) row[3]).doubleValue(),
                            (String) row[4],
                            (String) row[5],
                            attributesMap
                    )
            ));
        }
        return ResponseEntity.notFound().build();
    }

    private Map<String, Set<String>> parseAttributes(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, String>> attributesList = objectMapper.readValue(jsonString, new TypeReference<>() {});

            Map<String, Set<String>> attributesMap = new HashMap<>();
            for (Map<String, String> attr : attributesList) {
                String key = attr.get("attribute");
                String value = attr.get("value");

                if (key != null) {
                    attributesMap.computeIfAbsent(key, k -> new HashSet<>()).add(value);
                }
            }
            return attributesMap;
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }
}
