package com.example.deliveryapp.Security.Repository;

import com.example.deliveryapp.Security.Entity.ConfirmationToken;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

  ConfirmationToken findByToken(String token);

  @Modifying
  @Transactional
  @Query(
      value = "UPDATE confirmation_token ct SET ct.confirmed_at = ?2 WHERE ct.token = ?1",
      nativeQuery = true
  )
  void updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
