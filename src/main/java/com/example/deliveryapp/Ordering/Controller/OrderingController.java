package com.example.deliveryapp.Ordering.Controller;

import com.example.deliveryapp.Ordering.Entity.OrderTicket;
import com.example.deliveryapp.Ordering.Service.PlaceOrderService;
import com.example.deliveryapp.Ordering.model.CartRequest;
import com.example.deliveryapp.Ordering.model.OrderTicketJson;
import com.example.deliveryapp.Security.Model.JwtRequest;
import com.example.deliveryapp.Security.Model.JwtResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping
@Slf4j
@Tag(name = "Ordering") // edit the name of the class in openApi doc.
public class OrderingController {

  String body = "{\n"
      + "    \"restaurantId\":2,\n"
      + "    \"totalAmount\":500,\n"
      + "    \"foodList\": { \n"
      + "        \"foodInfoList\":[\n"
      + "            {\n"
      + "                \"foodId\": 52,\n"
      + "                \"foodName\": \"food1\",\n"
      + "                \"foodPrice\": 23,\n"
      + "                \"foodStockQuantity\": 100,\n"
      + "                \"qtyAdded\": 5\n"
      + "            },\n"
      + "            {\n"
      + "                \"foodId\": 53,\n"
      + "                \"foodName\": \"food2\",\n"
      + "                \"foodPrice\": 24,\n"
      + "                \"foodStockQuantity\": 100,\n"
      + "                \"qtyAdded\": 5\n"
      + "            }\n"
      + "        ]\n"
      + "    },\n"
      + "    \"addOnList\": { \n"
      + "        \"addOnInfoList\":[\n"
      + "            {\n"
      + "                \"addOnId\": 52,\n"
      + "                \"addOnName\": \"addOn1\",\n"
      + "                \"addOnPrice\": 23,\n"
      + "                \"addOnStockQuantity\": 100,\n"
      + "                \"qtyAdded\": 5\n"
      + "            },\n"
      + "            {\n"
      + "                \"addOnId\": 53,\n"
      + "                \"addOnName\": \"addOn2\",\n"
      + "                \"addOnPrice\": 24,\n"
      + "                \"addOnStockQuantity\": 100,\n"
      + "                \"qtyAdded\": 5\n"
      + "            }\n"
      + "        ]\n"
      + "    }\n"
      + "}";
  @Autowired
  PlaceOrderService placeOrderService;

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

  // More information about endPoint
  @Operation(
      description = "getAllOrders endPoint",
      summary = "Summary for getAllOrder endPoint",
      responses = {
          @ApiResponse(
              description = "Success",
              responseCode = "200"
          ),
          @ApiResponse(
              description = "Unauthorized/Invalid Token",
              responseCode = "401"
          )

      }
  )
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

  @GetMapping("/restTemplate")
  public ResponseEntity<Object> getWeather() throws JsonProcessingException {

    // PostForEntity gives response with body, headers, statusCode.
    // PostForObject gives only response.
    RestTemplate restTemplate = new RestTemplate();
    JwtRequest jwtRequest = new JwtRequest("manish1958@gmail.com", "1234");
    // getting token
    ResponseEntity<String> token = restTemplate.postForEntity(
        "http://localhost:8080/jwtLogin", jwtRequest,
        String.class);

    HttpHeaders headers = new HttpHeaders();
    ObjectMapper mapper = new ObjectMapper();
    JwtResponse jwtResponse = mapper.readValue(token.getBody(), JwtResponse.class);
    // setting token in headers
    headers.setBearerAuth(
        jwtResponse.getAccessToken());

    HttpEntity<Object> entity = new HttpEntity<>(headers);
    // exchange for restTemplate GET with token headers
    ResponseEntity<String> orders = restTemplate.exchange("http://localhost:8080/getAllOrders",
        HttpMethod.GET,
        entity,
        String.class);
    log.info("getAllOrders RestTemplate exchange with token in headers GET: {}", orders);

    // getForEntity for restTemplate GET with headers is not supported

    // getForEntity for restTemplate GET without token headers
    ResponseEntity<String> rest = restTemplate.getForEntity(
        "http://localhost:8080/getAllRestaurants",
        String.class);
    log.info("getAllRestaurants RestTemplate without headers GET: {}", rest);

    CartRequest cartRequest = mapper.readValue(body, CartRequest.class);
    // body and header setup
    HttpEntity<Object> entity1 = new HttpEntity<>(cartRequest, headers);

    // exchange for restTemplate POST with body, and token in headers
    ResponseEntity<String> orderAdded = restTemplate.exchange(
        "http://localhost:8080/placeOrder", HttpMethod.POST, entity1,
        String.class);
    log.info("placeOrder RestTemplate exchange with body, and token in headers POST: {}",
        orderAdded);
    // postForEntity for restTemplate POST with body, and token in headers
    ResponseEntity<String> orderAdded1 = restTemplate.postForEntity(
        "http://localhost:8080/placeOrder", entity1,
        String.class);
    log.info("placeOrder RestTemplate postForEntity with body, and token in headers POST: {}",
        orderAdded1);

    return new ResponseEntity<>(orderAdded1.getBody(), HttpStatus.OK);
  }


}
