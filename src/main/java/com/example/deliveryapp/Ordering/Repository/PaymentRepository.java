package com.example.deliveryapp.Ordering.Repository;

import com.example.deliveryapp.Ordering.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
