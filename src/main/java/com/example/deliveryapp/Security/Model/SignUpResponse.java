package com.example.deliveryapp.Security.Model;


import com.example.deliveryapp.Security.Entity.SignUp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class SignUpResponse {
    private String message;
    private SignUp signUpDetails;
}
