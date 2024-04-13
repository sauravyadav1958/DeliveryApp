package com.example.deliveryapp.Security.Entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
