package com.example.deliveryapp.Security.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ChangePasswordRequest {

  private String emailId;
  private String oldPassword;
  private String newPassword;
}
