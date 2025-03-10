package org.edu.main.repository;

import org.edu.main.dto.response.ProductResponse;
import org.edu.main.model.Address;
import org.edu.main.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

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
            where p.active = true 
            group by p.id, p.name, i.filePath, c.name
            """)
    Page<ProductResponse> getProducts(Pageable pageable);
}
