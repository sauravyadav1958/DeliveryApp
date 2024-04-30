package com.example.deliveryapp.Security.Service;


import com.example.deliveryapp.Security.Entity.RefreshToken;


public interface RefreshTokenService {

  public RefreshToken createRefreshToken(String emailId);

  public RefreshToken verifyExpiration(RefreshToken token);

  void delete(String refreshToken);
}
