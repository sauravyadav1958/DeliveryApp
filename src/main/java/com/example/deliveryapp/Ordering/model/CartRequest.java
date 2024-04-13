package com.example.deliveryapp.Ordering.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartRequest {

  private Long cartId;
  private Long restaurantId;
  private Long totalAmount;
  private FoodList foodList;
  private AddOnList addOnList;

}
