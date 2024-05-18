package com.example.deliveryapp.Security.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class UserSession {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long sessionId;
  @Column(unique = true)
  private Long userId;
  private String UUID;
  private LocalDateTime localDateTime;

  public UserSession(Long userId, String UUID, LocalDateTime localDateTime) {
    this.userId = userId;
    this.UUID = UUID;
    this.localDateTime = localDateTime;
  }
}
