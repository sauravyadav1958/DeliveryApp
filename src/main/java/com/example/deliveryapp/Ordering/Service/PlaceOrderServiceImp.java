package com.example.deliveryapp.Ordering.Service;

import com.example.deliveryapp.Ordering.Entity.Cart;
import com.example.deliveryapp.Ordering.Entity.OrderTicket;
import com.example.deliveryapp.Ordering.Entity.Payment;
import com.example.deliveryapp.Ordering.Repository.CartRepository;
import com.example.deliveryapp.Ordering.Repository.OrderRepository;
import com.example.deliveryapp.Ordering.model.AddOnList;
import com.example.deliveryapp.Ordering.model.CartRequest;
import com.example.deliveryapp.Ordering.model.FoodList;
import com.example.deliveryapp.Ordering.model.OrderTicketJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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

  @Override
  @Transactional
  public OrderTicketJson placeOrder(CartRequest cartRequest) throws JsonProcessingException {
    Payment payment = new Payment();
    payment.setTotalAmount(cartRequest.getTotalAmount());
    payment.setStatus("success");

    Cart cart = new Cart();
    cart.setRestaurantId(cartRequest.getRestaurantId());
    cart.setTotalAmount(cartRequest.getTotalAmount());

    String foodList = objectMapper.writeValueAsString(cartRequest.getFoodList());
    String addOnList = objectMapper.writeValueAsString(cartRequest.getAddOnList());

    cart.setFoodList(foodList);
    cart.setAddOnList(addOnList);
    cart.setPayment(payment);

    Cart savedCart = cartRepository.save(cart);

    OrderTicket orderTicket = new OrderTicket();
    orderTicket.setTotalAmount(cartRequest.getTotalAmount());
    orderTicket.setStatus(payment.getStatus());
    orderTicket.setCart(savedCart);
    orderTicket.setRestaurantId(cartRequest.getRestaurantId());

    OrderTicket savedOrderTicket = orderRepository.save(orderTicket);
    OrderTicketJson orderTicketJson = new OrderTicketJson();
    orderTicketJson.setOrderId(savedOrderTicket.getOrderId());
    orderTicketJson.setRestaurantId(savedOrderTicket.getRestaurantId());
    orderTicketJson.setTotalAmount(savedOrderTicket.getTotalAmount());
    orderTicketJson.setStatus(savedOrderTicket.getStatus());
    cartRequest.setCartId(cart.getCartId());
    orderTicketJson.setCartRequest(cartRequest);

    return orderTicketJson;
  }

  @Override
  public OrderTicket getOrder(Long orderId) {
    Optional<OrderTicket> savedOrderTicket = orderRepository.findById(orderId);
    return savedOrderTicket.get();
  }

  @Override
  public List<OrderTicket> getAllOrders() {
    List<OrderTicket> orderTicketList = orderRepository.findAll();
    return orderTicketList;
  }

  @Override
  public OrderTicket updateOrder(Long orderId, OrderTicket orderTicket) {
    System.out.println("orderTicket: " + orderTicket);
    orderTicket.setOrderId(orderId);
    OrderTicket savedOrderTicket = orderRepository.save(orderTicket);
    return savedOrderTicket;
  }
}
