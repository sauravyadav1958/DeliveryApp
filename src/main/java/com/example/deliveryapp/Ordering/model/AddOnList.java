package com.example.deliveryapp.Ordering.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddOnList {
  private List<AddOnInfo> addOnInfoList;
}
