package com.example.deliveryapp.Security.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ResetPasswordRequest {

  private String emailId;
  private String newPassword;
}
