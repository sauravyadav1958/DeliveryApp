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
  private String imageUrl;
  private String imageSearchKeyword;
  //  **************  OneToMany or ManyToOne uni-directional  **************
  //  Third table would be created in this for mapping(containing primary Key of both the tables),
  //  and here we can Access relation from one side only i.e. from oneToMany. (or ManyToOne in case the if it is ManyToOne uni-directional)
  //  only oneToMany or manyToOne annotation is done on single table(Here mappedBy is not used since it is unidirectional).

  //  **************  OneToMany or ManyToOne bi-directional  **************
  //  Here mapping is done by using the primary key of One(Restaurant) table inside Many(Foods) table
  //  and here we can Access relation from both sides(OneToMany and ManyToOne). (No third table is created)
  //  Both OneToMany and ManyToOne annotation are used.
  //  mappedBy is used here in OneToMany annotation for defining mapping. (ManyToOne can't have mappedBy property)
  //  toString method shouldn't have tables since it will give stackOverFlow error due to recursion. (Applicable for bi-directional only)

  @OneToMany(
      mappedBy = "restaurant",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  // TODO cannot simultaneously fetch multiple bags
  @Fetch(value = FetchMode.SUBSELECT)
//  @JsonIgnore
  private List<Food> foodList;

  @OneToMany(
      mappedBy = "restaurant",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY
  )
  // TODO cannot simultaneously fetch multiple bags
  // TODO use of @Fetch
  @Fetch(value = FetchMode.SUBSELECT)
//  @JsonIgnore
  private List<AddOn> addOnList;

  //  **************  ManyToMany uni-directional  **************
  //  Third table would be created in this for mapping(containing primary Key of both the tables),
  //  and here we can Access relation from one side only.
  //  only ManyToMany annotation is done on single table(Here mappedBy is not used since it is unidirectional).
  //

  //  **************  ManyToMany bi-directional  **************
  //  Third and fourth table would be created in this for mapping if mappedBy is not used.(containing primary Key of both the tables)
  //  and here we can Access relation from both sides.
  //  ManyToMany annotation are used on both tables.
  //  mappedBy can be used here in ManyToMany annotation for defining mapping. (Since we need only one table for mapping not both (hence only third table will be created))
  //  toString method shouldn't have tables since it will give stackOverFlow error due to recursion. (Applicable for bi-directional only)


  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//  @Fetch(value = FetchMode.SUBSELECT)
  private List<Customer> customerList;

  @Override
  public String toString() {
    return "Restaurant{" +
        "restaurantId=" + restaurantId +
        ", restaurantName='" + restaurantName + '\'' +
        ", address='" + address + '\'' +
        ", imageUrl='" + imageUrl + '\'' +
        ", imageSearchKeyword='" + imageSearchKeyword + '\'' +
        '}';
  }

  // this is to avoid stack overflow in bidirectional mapping
  // used where mappedBy is present
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
