package org.edu.main.repository;

import org.edu.main.model.Address;
import org.edu.main.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
