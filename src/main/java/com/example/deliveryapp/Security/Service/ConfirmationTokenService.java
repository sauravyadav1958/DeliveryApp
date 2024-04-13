package com.example.deliveryapp.Security.Service;


public interface ConfirmationTokenService {

  String confirm(String token);

  String resendConfirmation(String oldToken);
}
