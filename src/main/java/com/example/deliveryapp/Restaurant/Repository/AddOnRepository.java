package com.example.deliveryapp.Restaurant.Repository;

import com.example.deliveryapp.Restaurant.Entity.AddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn, Long> {

}
