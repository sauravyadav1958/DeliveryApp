package com.example.deliveryapp.Security.Repository;

import com.example.deliveryapp.Security.Entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

  RefreshToken findByRefreshToken(String refreshToken);
}
