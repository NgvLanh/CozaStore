package org.edu.main.repository;

import org.edu.main.dto.response.ProductDetailsResponse;
import org.edu.main.dto.response.ProductResponse;
import org.edu.main.model.Address;
import org.edu.main.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


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
                select p.id, p.name, 
                       min(s.price) as min_price, 
                       max(s.price) as max_price, 
                       p.description,
                       group_concat(distinct i.file_path separator ', ') as images,
                       json_arrayagg(json_object('attribute', a.name, 'value', ao.value)) as attributes
                from products p
                inner join skus s on p.id = s.product_id
                left join attribute_option_sku aos on s.id = aos.sku_id
                left join attribute_options ao on aos.attribute_option_id = ao.id
                left join attributes a on a.id = ao.attribute_id
                left join images i on i.product_id = p.id
                where p.id = ?1
                group by p.id, p.name, p.description
            """, nativeQuery = true)
    Object getProductDetails(long productId);
}
