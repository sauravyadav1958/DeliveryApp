package com.example.deliveryapp.Ordering.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderTicketJson {

  private Long orderId;
  private Long restaurantId;
  private CartRequest cartRequest;
  private Long totalAmount;
  private String status;
}
