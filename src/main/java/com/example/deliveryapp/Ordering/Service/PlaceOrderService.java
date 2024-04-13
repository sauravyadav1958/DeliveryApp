package com.example.deliveryapp.Ordering.Service;

import com.example.deliveryapp.Ordering.Entity.OrderTicket;
import com.example.deliveryapp.Ordering.model.CartRequest;
import com.example.deliveryapp.Ordering.model.OrderTicketJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;

public interface PlaceOrderService {

  OrderTicketJson placeOrder(CartRequest cartRequest) throws JsonProcessingException;

  OrderTicket getOrder(Long orderId);

  List<OrderTicket> getAllOrders();

  OrderTicket updateOrder(Long orderId, OrderTicket orderTicket);
}
