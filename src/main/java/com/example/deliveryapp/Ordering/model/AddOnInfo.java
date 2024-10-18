package com.example.deliveryapp.Ordering.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddOnInfo {
  private Long addOnId;
  private String addOnName;
  private String imageUrl;
  private String imageSearchKeyword;
  private String description;
  private double addOnPrice;
  private int addOnStockQuantity;
  private int qtyAdded;
}
