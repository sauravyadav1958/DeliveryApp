package com.example.deliveryapp.Ordering.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class OrderTicket {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderTicket_sequence")
  private Long orderId;
  private Long restaurantId;
  @OneToOne(
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER
  )
  @JoinColumn(
      name = "cart_id",
      referencedColumnName = "cartId"
  )
  private Cart cart;
  private Long totalAmount;
  private String status;

}
