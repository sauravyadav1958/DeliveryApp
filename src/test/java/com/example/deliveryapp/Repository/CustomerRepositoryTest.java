package com.example.deliveryapp.Repository;

import com.example.deliveryapp.Restaurant.Entity.Customer;
import com.example.deliveryapp.Restaurant.Entity.Restaurant;
import com.example.deliveryapp.Restaurant.Repository.CustomerRepository;
import com.example.deliveryapp.Restaurant.Repository.RestaurantRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Ignore
class CustomerRepositoryTest {

  @Autowired
  CustomerRepository customerRepository;
  @Autowired
  RestaurantRepository restaurantRepository;

  @Test
  public void saveCustomer() {

    Restaurant restaurant1 = Restaurant.builder()
        .restaurantName("veg")
        .address("")
        .foodList(new ArrayList<>())
        .build();

    Restaurant restaurant2 = Restaurant.builder()
        .restaurantName("non-veg")
        .address("")
        .foodList(new ArrayList<>())
        .build();

    Customer customer1 = Customer.builder()
        .address("")
        .contactNo("")
        .name("vegCustomer")
        .build();

    Customer customer2 = Customer.builder()
        .address("")
        .contactNo("")
        .name("HybridCustomer")
        .build();

    Customer customer3 = Customer.builder()
        .address("")
        .contactNo("")
        .name("HybridCustomer")
        .build();

    customer1.setRestaurantList(List.of(restaurant1));
    customer2.setRestaurantList(List.of(restaurant1, restaurant2));
    customer3.setRestaurantList(List.of(restaurant1, restaurant2));

    customerRepository.saveAll(List.of(customer1, customer2, customer3));

//    restaurant1.setCustomerList(List.of(customer1));
//    restaurant2.setCustomerList(List.of(customer2, customer3));
//
//
//    restaurantRepository.save(restaurant1);
//    restaurantRepository.save(restaurant2);

  }

  @Test
  public void getCustomer() {
    Optional<Customer> customer = customerRepository.findById((long) 102);
    Optional<Restaurant> restaurant = restaurantRepository.findById((long) 1402);
    System.out.println(customer);
  }

}