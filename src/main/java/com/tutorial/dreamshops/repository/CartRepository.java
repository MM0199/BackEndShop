package com.tutorial.dreamshops.repository;

import com.tutorial.dreamshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
    void deleteById(Long id);
}
