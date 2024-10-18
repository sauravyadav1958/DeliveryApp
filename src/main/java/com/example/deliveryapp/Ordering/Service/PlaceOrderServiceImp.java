package com.example.deliveryapp.Ordering.Service;

import com.example.deliveryapp.Configuration.Config;
import com.example.deliveryapp.Ordering.Entity.Cart;
import com.example.deliveryapp.Ordering.Entity.OrderTicket;
import com.example.deliveryapp.Ordering.Entity.Payment;
import com.example.deliveryapp.Ordering.Repository.CartRepository;
import com.example.deliveryapp.Ordering.Repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PlaceOrderServiceImp implements PlaceOrderService {

  @Autowired
  CartRepository cartRepository;
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  Config config;

  @Override
  @Transactional
  public OrderTicket placeOrder(Map<String, Object> map)
      throws JsonProcessingException, RazorpayException {

    RazorpayClient razorpay = new RazorpayClient(config.getRzp_key_id(),
        config.getRzp_key_secret());

    JSONObject orderRequest = new JSONObject();
    orderRequest.put("amount", map.get("amount"));
    orderRequest.put("currency", "INR");
    orderRequest.put("receipt", "receipt#1");

    JSONObject notes = new JSONObject();
    notes.put("notes_key_1", map.get("foodList"));
    orderRequest.put("notes", notes);

    Order order = razorpay.orders.create(orderRequest);

    Payment payment = new Payment();
    payment.setTotalAmount(Double.parseDouble(map.get("amount").toString()));
    payment.setStatus("pending");

    Cart cart = new Cart();
    Gson gson = new Gson();

    String restaurant = gson.toJson(map.get("restaurant"), LinkedHashMap.class);
    JSONObject restaurantJson = new JSONObject(restaurant);

    cart.setRestaurantId(Long.parseLong(restaurantJson.get("id").toString()));
    cart.setTotalAmount(Double.parseDouble(map.get("amount").toString()));

    String foodList = objectMapper.writeValueAsString(map.get("foodList"));
    String addOnList = objectMapper.writeValueAsString("");

    cart.setFoodList(foodList);
    cart.setAddOnList(addOnList);
    cart.setPayment(payment);

    Cart savedCart = cartRepository.save(cart);

    OrderTicket orderTicket = new OrderTicket();
    orderTicket.setOrderId(order.toJson().get("id").toString());
    orderTicket.setAmount_paid(Double.parseDouble(order.toJson().get("amount_paid").toString()));
    orderTicket.setAmount_due(Double.parseDouble(order.toJson().get("amount_due").toString()));
    orderTicket.setCurrency(order.toJson().get("currency").toString());
    orderTicket.setReceipt(order.toJson().get("receipt").toString());
    orderTicket.setOffer_id(order.toJson().get("offer_id").toString());
    orderTicket.setEntity(order.toJson().get("entity").toString());
    orderTicket.setStatus(order.toJson().get("status").toString());
    orderTicket.setAttempts(Integer.parseInt(order.toJson().get("attempts").toString()));
    orderTicket.setNotes(order.toJson().get("notes").toString());
    Instant instant = Instant.ofEpochMilli(
        Long.parseLong(order.toJson().get("created_at").toString()));
    Timestamp timestamp = Timestamp.from(instant);
    orderTicket.setCreated_at(timestamp);
    orderTicket.setAmount(Double.parseDouble(order.toJson().get("amount").toString()));
    orderTicket.setCart(savedCart);
    orderTicket.setRestaurantId(Long.parseLong(restaurantJson.get("id").toString()));

    OrderTicket savedOrderTicket = orderRepository.save(orderTicket);

    return savedOrderTicket;
  }

  @Override
  public OrderTicket getOrder(String orderId) {
    Optional<OrderTicket> savedOrderTicket = orderRepository.findByOrderId(orderId);
    return savedOrderTicket.get();
  }

  @Override
  public List<OrderTicket> getAllOrders() {
    List<OrderTicket> orderTicketList = orderRepository.findAll();
    return orderTicketList;
  }

  @Override
  public OrderTicket updateOrder(String orderId, OrderTicket orderTicket) {
    System.out.println("orderTicket: " + orderTicket);
    orderTicket.setOrderId(orderId);
    OrderTicket savedOrderTicket = orderRepository.save(orderTicket);
    return savedOrderTicket;
  }
}
