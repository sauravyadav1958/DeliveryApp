package com.example.deliveryapp.Ordering.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_sequence")
  private Long Id;
  private String razorpayPaymentId;
  private Double totalAmount;
  private String status;
//  @OneToOne(
//      cascade = CascadeType.ALL,
//      fetch = FetchType.LAZY,
//      optional = false
//  )
//  @JoinColumn(
//      name = "cart_id",
//      referencedColumnName = "cartId"
//  )
//  @OneToOne
//  private Cart cart;

}
