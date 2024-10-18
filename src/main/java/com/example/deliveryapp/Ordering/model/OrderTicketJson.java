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

  private String orderId;
  private Long restaurantId;
  private FoodInfo foodInfo;
  private AddOnInfo addOnInfo;
  private double totalAmount;
  private String status;
}
