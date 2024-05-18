package com.example.deliveryapp.Security.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Login {

  @Id
  @NonNull
  private Long userId;
  private String emailId;
  private String password;

  public Login(String emailId, String password) {
    this.emailId = emailId;
    this.password = password;
  }
//  private Long userId;
//  @ManyToOne(cascade = CascadeType.DETACH)
//  @JoinColumn(name = "user_id", referencedColumnName = "userId")
//  private SignUp signUp;
}
