package com.example.deliveryapp.Restaurant.Repository;

import com.example.deliveryapp.Restaurant.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


}
