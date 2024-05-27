package com.example.deliveryapp.Restaurant.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Restaurant {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_Sequence")
  private Long restaurantId;
  private String restaurantName;
  private String address;


  @OneToMany(
      mappedBy = "restaurant",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )

  @Fetch(value = FetchMode.SUBSELECT)

  private List<Food> foodList;

  @OneToMany(
      mappedBy = "restaurant",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )

  @Fetch(value = FetchMode.SUBSELECT)

  private List<AddOn> addOnList;


  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)

  private List<Customer> customerList;

  @Override
  public String toString() {
    return "Restaurant{" +
        "restaurantId=" + restaurantId +
        ", restaurantName='" + restaurantName + '\'' +
        ", address='" + address + '\'' +
        '}';
  }


  @JsonManagedReference
  public void setFoodList(List<Food> foodList) {
    this.foodList = foodList;
  }

//  @JsonManagedReference
//  public List<Food> getFoodList() {
//    return foodList;
//  }
//
//  @JsonManagedReference
//  public List<AddOn> getAddOnList() {
//    return addOnList;
//  }

  @JsonManagedReference
  public void setAddOnList(List<AddOn> addOnList) {
    this.addOnList = addOnList;
  }


}
