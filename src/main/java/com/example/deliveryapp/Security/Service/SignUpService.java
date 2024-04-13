package com.example.deliveryapp.Security.Service;


import com.example.deliveryapp.Security.Model.SignUpRequest;
import com.example.deliveryapp.Security.Model.SignUpResponse;

public interface SignUpService {

  SignUpResponse signUp(SignUpRequest signUpRequest);


}
