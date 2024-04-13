package com.example.deliveryapp.Security.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
