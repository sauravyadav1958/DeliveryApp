package com.example.deliveryapp.Ordering.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import java.sql.Timestamp;
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
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderTicket_sequence")
  private String orderId;
  private String entity;
  private double amount;
  private double amount_paid;
  private double amount_due;
  private String currency;
  private String receipt;
  private String offer_id;
  private String status;
  private int attempts;
  @Lob
  @Column(columnDefinition = "LONGTEXT")
  private String notes;
  private Timestamp created_at;
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

}
