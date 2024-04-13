package com.example.deliveryapp.Security.Repository;

import com.example.deliveryapp.Security.Entity.UserSession;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {


  public Optional<UserSession> findByUserId(Long userId);
}
