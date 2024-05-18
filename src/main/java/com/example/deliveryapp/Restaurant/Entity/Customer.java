package com.example.deliveryapp.Restaurant.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerDetails_sequence")
  private Long customerId;
  private String name;
  private String address;
  private String contactNo;

  @ManyToMany(mappedBy = "customerList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Restaurant> restaurantList;

  // ManyToMany gives stackoverflow error hence we can ignore This function
  @JsonIgnore
  public List<Restaurant> getRestaurantList() {
    return restaurantList;
  }


}
