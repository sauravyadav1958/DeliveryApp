package com.example.deliveryapp.Restaurant.Service;

import com.example.deliveryapp.Restaurant.Entity.Restaurant;
import java.util.List;

public interface RestaurantService {

  Restaurant saveRestaurant(Restaurant restaurant);

  Restaurant updateRestaurant(Long restaurantId, Restaurant restaurant);

  Restaurant delete(Long restaurantId);

  Restaurant getRestaurantById(Long restaurantId);

  List<Restaurant> getAllRestaurants();
}
