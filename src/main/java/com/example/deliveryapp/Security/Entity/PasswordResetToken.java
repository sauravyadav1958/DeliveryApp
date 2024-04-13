package com.example.deliveryapp.Security.Entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
