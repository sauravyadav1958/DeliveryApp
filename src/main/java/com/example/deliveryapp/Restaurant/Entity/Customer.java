package com.example.deliveryapp.Restaurant.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
