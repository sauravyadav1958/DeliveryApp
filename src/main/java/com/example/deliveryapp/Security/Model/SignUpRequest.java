package com.example.deliveryapp.Security.Model;


import com.example.deliveryapp.Security.Enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SignUpRequest {

  private String firstName;
  private String lastName;
  private String emailId;
  private String password;
  private UserRole role;

}
