package com.example.deliveryapp.Ordering.Controller;

import com.example.deliveryapp.Ordering.Entity.OrderTicket;
import com.example.deliveryapp.Ordering.Repository.CartRepository;
import com.example.deliveryapp.Ordering.Repository.OrderRepository;
import com.example.deliveryapp.Ordering.Service.PlaceOrderService;
import com.example.deliveryapp.Ordering.model.CartRequest;
import com.example.deliveryapp.Ordering.model.OrderTicketJson;
import com.example.deliveryapp.Restaurant.Repository.AddOnRepository;
import com.example.deliveryapp.Restaurant.Repository.FoodRepository;
import com.example.deliveryapp.Restaurant.Repository.RestaurantRepository;
import com.example.deliveryapp.Restaurant.Service.RestaurantService;
import com.example.deliveryapp.Security.Repository.ConfirmationTokenRepository;
import com.example.deliveryapp.Security.Repository.LoginRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.Repository.UserSessionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class OrderingController {

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


  @PostMapping("/placeOrder")
  public ResponseEntity<OrderTicketJson> placeOrder(@RequestBody CartRequest cartRequest)
      throws JsonProcessingException {

    OrderTicketJson savedOrderTicket = placeOrderService.placeOrder(cartRequest);
    return new ResponseEntity<OrderTicketJson>(savedOrderTicket, HttpStatus.OK);
  }

  @GetMapping("/getOrder/{orderId}")
  public ResponseEntity<OrderTicket> getOrder(@PathVariable Long orderId) {
    OrderTicket savedOrderTicket = placeOrderService.getOrder(orderId);

    return new ResponseEntity<OrderTicket>(savedOrderTicket, HttpStatus.OK);


  }

  @GetMapping("/getAllOrders")
  public ResponseEntity<List<OrderTicket>> getAllOrders() {
    List<OrderTicket> orderTicketList = placeOrderService.getAllOrders();

    return new ResponseEntity<List<OrderTicket>>(orderTicketList, HttpStatus.OK);
  }

  @PutMapping("/updateOrder/{orderId}")
  public ResponseEntity<OrderTicket> updateOrder(@PathVariable Long orderId,
      @RequestBody OrderTicket orderTicket) {
    OrderTicket updatedOrderTicket = placeOrderService.updateOrder(orderId, orderTicket);

    return new ResponseEntity<OrderTicket>(updatedOrderTicket, HttpStatus.OK);
  }


}
