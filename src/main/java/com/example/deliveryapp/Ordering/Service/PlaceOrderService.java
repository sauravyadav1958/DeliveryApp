package com.example.deliveryapp.Ordering.Service;

import com.example.deliveryapp.Ordering.Entity.OrderTicket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.razorpay.RazorpayException;
import java.util.List;
import java.util.Map;

public interface PlaceOrderService {

  OrderTicket placeOrder(Map<String, Object> map)
      throws JsonProcessingException, RazorpayException;

  OrderTicket getOrder(String orderId);

  List<OrderTicket> getAllOrders();

  OrderTicket updateOrder(String orderId, OrderTicket orderTicket);
}
