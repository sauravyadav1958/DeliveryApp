package com.example.deliveryapp.SignUp.Repository;

import com.example.deliveryapp.Restaurant.Entity.AddOn;
import com.example.deliveryapp.Ordering.Entity.Cart;
import com.example.deliveryapp.Restaurant.Entity.Food;
import com.example.deliveryapp.Ordering.Entity.Payment;
import com.example.deliveryapp.Ordering.Repository.CartRepository;
import com.example.deliveryapp.Ordering.Repository.OrderRepository;
import com.example.deliveryapp.Ordering.Repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Ignore
class CartRepositoryTest {

  @Autowired
  CartRepository cartRepository;
  @Autowired
  PaymentRepository paymentRepository;
  @Autowired
  OrderRepository orderRepository;

  @Test
  public void saveCart() throws Exception {
    Food food1 = Food.builder()
        .foodId(1L)
//        .restaurantId(152L)
        .foodName("food1")
        .foodPrice(23)
        .build();

    Food food2 = Food.builder()
        .foodId(2L)
//        .restaurantId(152L)
        .foodName("food2")
        .foodPrice(24)
        .build();

    AddOn addOn1 = AddOn.builder()
        .addOnId(1L)
//        .restaurantId(152L)
        .addOnName("addOn1")
        .addOnPrice(23)
        .build();

    AddOn addOn2 = AddOn.builder()
        .addOnId(2L)
//        .restaurantId(152L)
        .addOnName("addOn2")
        .addOnPrice(24)
        .build();

    ObjectMapper objectMapper = new ObjectMapper();
    String food1Json = objectMapper.writeValueAsString(food1);
    String food2Json = objectMapper.writeValueAsString(food2);
    String addOn1Json = objectMapper.writeValueAsString(addOn1);
    String addOn2Json = objectMapper.writeValueAsString(addOn2);

    String foodJson = Arrays.asList(food1Json, food2Json).toString();
    String addOnJson = Arrays.asList(addOn1Json, addOn2Json).toString();

    Cart cart = Cart.builder()
        .totalAmount(500L)
        .restaurantId(152L)
//        .foodWithQty(foodJson)
//        .addOnWithQty(addOnJson)
        .build();

//    Cart cartSaved = cartRepository.save(cart);

    Payment payment = Payment.builder()
//        .cart(cart)
        .status("success")
        .totalAmount(500L)
        .build();

    cart.setPayment(payment);

cartRepository.save(cart);

//    Payment paymentSaved = paymentRepository.save(payment);


//    OrderTicket orderTicket = OrderTicket.builder()
//        .cartId(paymentSaved.getCart().getCartId())
//        .restaurantId(152L)
//        .totalAmount(500L)
//        .status("Received")
//        .foodWithQty(foodJson)
//        .addOnWithQty(addOnJson)
//        .build();
//
//      orderRepository.save(orderTicket);


  }


}