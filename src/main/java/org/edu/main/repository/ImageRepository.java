package org.edu.main.repository;

import org.edu.main.model.Image;
import org.edu.main.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
