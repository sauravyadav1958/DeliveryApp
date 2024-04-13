package com.example.deliveryapp.Restaurant.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_sequence")
  private Long foodId;
  private String foodName;
  private int foodPrice;
  private int foodStockQuantity;

  @ManyToOne(
      cascade = CascadeType.ALL
  )
  @JoinColumn(
      name = "restaurant_id"
  )
  private Restaurant restaurant;
  // this is to avoid stack overflow in bidirectional mapping
  // used where mappedBy is not present
  @JsonBackReference
  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }
//    @JsonBackReference
//    public Restaurant getRestaurant() {
//      return restaurant;
//    }

}
