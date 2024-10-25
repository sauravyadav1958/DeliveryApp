package com.example.deliveryapp.Ordering.Repository;

import com.example.deliveryapp.Ordering.Entity.OrderTicket;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderTicket, Long> {
  Optional<OrderTicket> findByOrderId(String orderId);

}
