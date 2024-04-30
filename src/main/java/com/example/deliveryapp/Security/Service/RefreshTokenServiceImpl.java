package com.example.deliveryapp.Security.Service;


import com.example.deliveryapp.Security.Entity.RefreshToken;
import com.example.deliveryapp.Security.Repository.RefreshTokenRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.utility.JWTUtility;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Autowired
  RefreshTokenRepository refreshTokenRepository;

  @Autowired
  SignUpRepository signUpRepository;
  @Autowired
  private JWTUtility jwtUtility;

  public RefreshToken createRefreshToken(String refreshToken) {
    String userName = jwtUtility.getUsernameFromToken(refreshToken);
    Date expiryDate = jwtUtility.getExpirationDateFromToken(refreshToken);
    RefreshToken refreshTokenSaved = RefreshToken.builder()
        .signUp(signUpRepository.findByEmailId(userName))
        .refreshToken(refreshToken)
        .expiryDate(expiryDate)
        .build();
    return refreshTokenRepository.save(refreshTokenSaved);
  }

  public RefreshToken verifyExpiration(RefreshToken refreshToken) {
    if (refreshToken.getExpiryDate().before(Date.from(Calendar.getInstance().toInstant()))) {
      refreshTokenRepository.delete(refreshToken);
      throw new RuntimeException(
          refreshToken.getRefreshToken() + " Refresh token is expired. Please make a new login..!");
    }
    return refreshToken;
  }

  @Override
  public void delete(String refreshToken) {
    RefreshToken refreshTokenDeleted = refreshTokenRepository.findByRefreshToken(
        refreshToken);
    refreshTokenRepository.delete(refreshTokenDeleted);
  }
}
