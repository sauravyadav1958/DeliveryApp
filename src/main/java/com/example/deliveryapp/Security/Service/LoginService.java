package com.example.deliveryapp.Security.Service;


import com.example.deliveryapp.Security.Model.JwtRequest;
import com.example.deliveryapp.Security.Model.JwtResponse;
import com.example.deliveryapp.Security.Model.LogInRequest;
import com.example.deliveryapp.Security.Model.RefreshTokenRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

  String login(LogInRequest loginInRequest, HttpServletResponse response);

  String logout(Long userId);

  JwtResponse jwtLogin(JwtRequest jwtRequest, HttpServletResponse response) throws Exception;

  JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest, HttpServletResponse response);

  String jwtLogout(HttpServletResponse response);
}
