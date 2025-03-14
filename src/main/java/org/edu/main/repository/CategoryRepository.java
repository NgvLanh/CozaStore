package org.edu.main.repository;

import org.edu.main.model.Banner;
import org.edu.main.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByActiveTrue();
}
