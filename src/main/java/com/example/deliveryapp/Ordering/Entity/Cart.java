package com.example.deliveryapp.Ordering.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence")
  private Long cartId;
  private Long restaurantId;
  private Long totalAmount;
  private String foodList;
  private String addOnList;


  @OneToOne(cascade = CascadeType.ALL,

      fetch = FetchType.EAGER
  )
  private Payment payment;


}
