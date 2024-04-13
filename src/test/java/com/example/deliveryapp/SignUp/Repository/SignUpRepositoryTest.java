package com.example.deliveryapp.SignUp.Repository;

import com.example.deliveryapp.Security.Entity.SignUp;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Ignore
class SignUpRepositoryTest {

  @Autowired
  private SignUpRepository signUpRepository;

  @Test
  @Ignore
  public void SaveSignUp() {
    SignUp signUp = SignUp.builder()
        .firstName("manish")
        .lastName("singh")
        .emailId("dummy@gmail.com")
        .password("password")
        .build();

    signUpRepository.save(signUp);
  }

  @Ignore
  @Test
  public void findByFirstName() {

    List<SignUp> signUpList = signUpRepository.findByFirstName("saurav");
    Assertions.not(null);
  }

}