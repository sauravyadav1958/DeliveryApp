package com.example.deliveryapp.Ordering.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FoodInfo {
  private Long foodId;
  private String foodName;
  private String imageUrl;
  private String imageSearchKeyword;
  private String description;
  private double foodPrice;
  private int foodStockQuantity;
  private int qtyAdded;
}
