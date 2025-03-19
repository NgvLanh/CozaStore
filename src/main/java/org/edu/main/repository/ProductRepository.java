package org.edu.main.repository;

import org.edu.main.dto.response.ProductDetailsResponse;
import org.edu.main.dto.response.ProductResponse;
import org.edu.main.model.Address;
import org.edu.main.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByActiveTrue(Pageable pageable);

    @Query("""
            select new org.edu.main.dto.response.ProductResponse(
            p.id, 
            p.name, 
            min(s.price),  
            max(s.price), 
            i.filePath, 
            c.name) 
            from products p
            join skus s on s.product.id = p.id
            join images i on i.product.id = p.id
            join categories c on c.id = p.category.id
            where p.active = true and i.main = true
            group by p.id, p.name, i.filePath, c.name
            """)
    Page<ProductResponse> getProducts(Pageable pageable);

    @Query(value = """
            SELECT s.id AS sku_id,
                   p.name,
                   p.description,
                   s.code,
                   s.price,
                   s.stock,
                   JSON_ARRAYAGG(JSON_OBJECT('attribute', a.name, 'value', ao.value)) AS attributes,
                   GROUP_CONCAT(DISTINCT i.file_path SEPARATOR ', ') AS images
            FROM skus s
            JOIN attribute_option_sku aos ON s.id = aos.sku_id
            JOIN attribute_options ao ON aos.attribute_option_id = ao.id
            JOIN attributes a ON ao.attribute_id = a.id
            JOIN products p ON s.product_id = p.id
            LEFT JOIN images i ON i.product_id = p.id
            WHERE s.product_id = ?1
            GROUP BY s.id
            """, nativeQuery = true)
    List<ProductDetailsResponse> getProductDetails(long productId);
}
