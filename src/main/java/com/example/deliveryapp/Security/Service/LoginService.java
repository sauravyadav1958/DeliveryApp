package com.example.deliveryapp.Security.Service;


import com.example.deliveryapp.Security.Model.ChangePasswordRequest;
import com.example.deliveryapp.Security.Model.LogInRequest;

public interface LoginService {

  String login(LogInRequest loginInRequest);

  String logout(Long userId);

}
