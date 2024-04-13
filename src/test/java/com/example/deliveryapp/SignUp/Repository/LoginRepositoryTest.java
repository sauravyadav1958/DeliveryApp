package com.example.deliveryapp.SignUp.Repository;

import com.example.deliveryapp.Security.Entity.Login;
import com.example.deliveryapp.Security.Entity.SignUp;
import com.example.deliveryapp.Security.Repository.LoginRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Ignore
class LoginRepositoryTest {

  @Autowired
  LoginRepository loginRepository;

  @Autowired
  private SignUpRepository signUpRepository;

  @Test
  public void doLogin() {

    SignUp signUp = signUpRepository.findByEmailId("d@gmail.com");
    Long userId;
    if (signUp != null) {
      userId = signUp.getUserId();
    } else {
      System.out.println("user is not valid !!!");
      return;
    }

    Login login = Login.builder()
        .emailId("dummy@gmail.com")
        .password("password")
        .userId(userId)
        .build();

    loginRepository.save(login);

  }

}