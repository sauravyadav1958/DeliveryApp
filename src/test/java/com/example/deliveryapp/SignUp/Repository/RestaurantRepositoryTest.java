package com.example.deliveryapp.SignUp.Repository;

import com.example.deliveryapp.Restaurant.Entity.Food;
import com.example.deliveryapp.Restaurant.Entity.Restaurant;
import com.example.deliveryapp.Restaurant.Repository.AddOnRepository;
import com.example.deliveryapp.Restaurant.Repository.FoodRepository;
import com.example.deliveryapp.Restaurant.Repository.RestaurantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.Optional;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Ignore
class RestaurantRepositoryTest {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Autowired
  FoodRepository foodRepository;
  @Autowired
  AddOnRepository addOnRepository;

  @Test
  public void saveRestaurant() throws JsonProcessingException {

    Restaurant restaurant = Restaurant.builder()
        .restaurantName("restaurant1")
//        .addOnList("")
//        .foodList("")
        .address("addressRestaurant1")

        .build();

//    restaurant = restaurantRepository.save(restaurant);

    Food food1 = Food.builder()
//        .restaurant(restaurant)
        .foodName("food1")
        .foodPrice(23)
        .build();

    Food food2 = Food.builder()
//        .restaurant(restaurant)
        .foodName("food2")
        .foodPrice(24)
        .build();

//    restaurant.setFoodList(List.of(food1, food2));
//
//restaurantRepository.save(restaurant);

    food1.setRestaurant(restaurant);
    food2.setRestaurant(restaurant);

    foodRepository.saveAll(List.of(food1, food2));

//    List<Food> food = foodRepository.saveAll(List.of(food1, food2));
//    restaurant = food.get(0).getRestaurant();
//
//    AddOn addOn1 = AddOn.builder()
//        .restaurantId(restaurant.getRestaurantId())
//        .addOnName("addOn1")
//        .addOnPrice(23)
////        .restaurant(restaurant)
//        .build();
//
//    AddOn addOn2 = AddOn.builder()
//        .restaurantId(restaurant.getRestaurantId())
//        .addOnName("addOn2")
//        .addOnPrice(24)
////        .restaurant(restaurant)
//        .build();
//
//    addOnRepository.saveAll(List.of(addOn1, addOn2));
//
//    ObjectMapper objectMapper = new ObjectMapper();
//
//    String food1Json = objectMapper.writeValueAsString(food1);
//    String food2Json = objectMapper.writeValueAsString(food2);
//    String addOn1Json = objectMapper.writeValueAsString(addOn1);
//    String addOn2Json = objectMapper.writeValueAsString(addOn2);
//
//    String foodJson = Arrays.asList(food1Json, food2Json).toString();
//    String addOnJson = Arrays.asList(addOn1Json, addOn2Json).toString();

//    restaurant.setFoodList(foodJson);
//    restaurant.setAddOnList(addOnJson);

//    restaurantRepository.save(restaurant);






  }

  @Test
  public void getRestaurant() {
    Optional<Restaurant> restaurant = restaurantRepository.findById((long)1202);
    System.out.println(restaurant.get());
  }

}