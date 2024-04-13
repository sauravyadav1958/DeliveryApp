package com.example.deliveryapp.Restaurant.Service;

import com.example.deliveryapp.Restaurant.Entity.AddOn;
import com.example.deliveryapp.Restaurant.Entity.Food;
import com.example.deliveryapp.Restaurant.Entity.Restaurant;
import com.example.deliveryapp.Restaurant.Repository.RestaurantRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantServiceImp implements RestaurantService {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Override
  public Restaurant saveRestaurant(Restaurant restaurant) {
    List<Food> foodList = restaurant.getFoodList();
    List<AddOn> addOnList = restaurant.getAddOnList();
    log.info("restaurant: {}", restaurant);

    foodList.stream().forEach(f -> f.setRestaurant(restaurant));
    addOnList.stream().forEach(a -> a.setRestaurant(restaurant));

    Restaurant savedRestaurant = restaurantRepository.save(restaurant);
    return savedRestaurant;
  }

  @Override
  public Restaurant updateRestaurant(Long restaurantId, Restaurant restaurant) {
    List<Food> foodList = restaurant.getFoodList();
    List<AddOn> addOnList = restaurant.getAddOnList();
    log.info("restaurant: " + restaurant);
    if (restaurant.getRestaurantId() == null) {
      restaurant.setRestaurantId(restaurantId);
    }
    foodList.stream().forEach(f -> f.setRestaurant(restaurant));
    addOnList.stream().forEach(a -> a.setRestaurant(restaurant));

    Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
    return updatedRestaurant;
  }

  @Override
  public Restaurant delete(Long restaurantId) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
    if (restaurant.isPresent()) {
      restaurantRepository.delete(restaurant.get());
      return restaurant.get();
    }
    return null;
  }

  @Override
  public Restaurant getRestaurantById(Long restaurantId) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
    if (restaurant.isPresent()) {
      return restaurant.get();
    }
    return null;
  }

  @Override
  public List<Restaurant> getAllRestaurants() {
    List<Restaurant> restaurantList = restaurantRepository.findAll();
    return restaurantList;
  }
}
