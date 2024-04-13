package com.example.deliveryapp.Ordering.Repository;

import com.example.deliveryapp.Ordering.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
