package com.example.deliveryapp.Security.Model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

  private String accessToken;
  private Date expiresAt;
  private String refreshToken;
  private Date refreshExpiresAt;
  private String tokenType;

}
