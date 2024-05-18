package com.example.deliveryapp.Security.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ConfirmationToken {


  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long confirmationTokenId;
  private String token;
  private LocalDateTime createdAt;
  private LocalDateTime expiresAt;
  private LocalDateTime confirmedAt;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private SignUp signUp;

  @JsonBackReference
  public void setSignUp(SignUp signUp) {
    this.signUp = signUp;
  }

  public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt,
      SignUp signUp) {
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.signUp = signUp;
  }

  @Override
  public String toString() {
    return "ConfirmationToken{" +
        "confirmationTokenId=" + confirmationTokenId +
        ", token='" + token + '\'' +
        ", createdAt=" + createdAt +
        ", expiresAt=" + expiresAt +
        ", confirmedAt=" + confirmedAt +
        '}';
  }
}
