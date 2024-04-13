package com.example.deliveryapp.Ordering.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
