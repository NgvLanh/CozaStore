package org.edu.main.repository;

import org.edu.main.model.Cart;
import org.edu.main.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
