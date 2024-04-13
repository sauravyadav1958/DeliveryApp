package com.example.deliveryapp.Restaurant.Controller;

import com.example.deliveryapp.Ordering.Repository.CartRepository;
import com.example.deliveryapp.Ordering.Repository.OrderRepository;
import com.example.deliveryapp.Restaurant.Entity.Restaurant;
import com.example.deliveryapp.Restaurant.Repository.AddOnRepository;
import com.example.deliveryapp.Restaurant.Repository.FoodRepository;
import com.example.deliveryapp.Restaurant.Repository.RestaurantRepository;
import com.example.deliveryapp.Security.Repository.ConfirmationTokenRepository;
import com.example.deliveryapp.Security.Repository.LoginRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.Repository.UserSessionRepository;
import com.example.deliveryapp.Ordering.Service.PlaceOrderService;
import com.example.deliveryapp.Restaurant.Service.RestaurantService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class RestaurantController {

  @Autowired
  RestaurantRepository restaurantRepository;
  @Autowired
  FoodRepository foodRepository;
  @Autowired
  AddOnRepository addOnRepository;
  @Autowired
  SignUpRepository signUpRepository;

  @Autowired
  UserSessionRepository userSessionRepository;
  @Autowired
  LoginRepository loginRepository;

  @Autowired
  ConfirmationTokenRepository confirmationTokenRepository;

  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  OrderRepository orderRepository;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  PlaceOrderService placeOrderService;

  @Autowired
  RestaurantService restaurantService;

  @PostMapping("/admin/saveRestaurant")
  public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant) {
    Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);

    log.info("savedRestaurant: {}", savedRestaurant);
    return new ResponseEntity<Restaurant>(savedRestaurant, HttpStatus.CREATED);
  }

  // Addition/update of Food, AddOns in Restaurant
  // Can give certain offer to customer, no addition/update other details
  @PutMapping("/admin/updateRestaurant/{restaurantId}")
  public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long restaurantId,
      @RequestBody Restaurant restaurant) {
    Restaurant updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurant);
    log.info("updatedRestaurant: {}", updatedRestaurant);
    return new ResponseEntity<Restaurant>(updatedRestaurant, HttpStatus.OK);
  }


  @GetMapping("/getRestaurant/{restaurantId}")
  public ResponseEntity<Restaurant> getRestaurant(@PathVariable("restaurantId") Long restaurantId) {
    Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

    return new ResponseEntity<Restaurant>(restaurant, HttpStatus.FOUND);
  }

  // Todo
  @GetMapping("/getAllRestaurants")
  public ResponseEntity<List<Restaurant>> getAllRestaurants() {
    List<Restaurant> restaurantList = restaurantService.getAllRestaurants();

    log.debug("restaurantList: {}", restaurantList);
    return new ResponseEntity<List<Restaurant>>(restaurantList, HttpStatus.OK);
  }

  // ToDO delete , restaurant should get removed from customer Details,
  //  cart shouldn't get affected, may be we can put restaurantId null in cart

  @DeleteMapping("/admin/deleteRestaurant/{restaurantId}")
  public ResponseEntity<Restaurant> deleteRestaurant(
      @PathVariable("restaurantId") Long restaurantId) {
    Restaurant deletedRestaurant = restaurantService.delete(restaurantId);
    if(deletedRestaurant != null) {
      return new ResponseEntity<Restaurant>(deletedRestaurant, HttpStatus.OK);
    }
    return null;
  }

  // search through food, etc



}
