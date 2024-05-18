package com.example.deliveryapp.Security.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PasswordResetToken {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long passwordResetTokenId;
  private String token;
  private LocalDateTime resetDoneAt;
  private LocalDateTime createdAt;
  private LocalDateTime expiresAt;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "userId")
  private SignUp signUp;

  public PasswordResetToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt) {
    this.token = token;
    this.expiresAt = expiresAt;
    this.createdAt = createdAt;
  }


}
