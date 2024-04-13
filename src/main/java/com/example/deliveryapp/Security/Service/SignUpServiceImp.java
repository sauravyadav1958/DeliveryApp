package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Security.Entity.ConfirmationToken;
import com.example.deliveryapp.Security.Entity.SignUp;
import com.example.deliveryapp.Security.Model.SignUpRequest;
import com.example.deliveryapp.Security.Model.SignUpResponse;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.Service.email.EmailSenderService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SignUpServiceImp implements SignUpService {

  @Autowired
  SignUpRepository signUpRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  EmailSenderService emailSenderService;
  @Autowired
  SignUpResponse signUpResponse;

  @Override
  public SignUpResponse signUp(SignUpRequest signUpRequest) {
    SignUp signUpExisting = signUpRepository.findByEmailId(signUpRequest.getEmailId());
    log.info("signUpExisting: {} ", signUpExisting);
    if (signUpExisting != null) {
      signUpResponse.setSignUpDetails(signUpExisting);
      signUpResponse.setMessage("User already exist.");
      return signUpResponse;
    }

    SignUp signUp = new SignUp(signUpRequest.getEmailId(), signUpRequest.getFirstName(),
        signUpRequest.getLastName(), passwordEncoder.encode(signUpRequest.getPassword()),
        signUpRequest.getRole());
    String token = UUID.randomUUID().toString();
    ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15), signUp);
    signUp.setConfirmationTokenList(List.of(confirmationToken));
    SignUp savedSignUp = signUpRepository.save(signUp);

    String link = "http://127.0.0.1:8080/confirm?token=" + token;
    System.out.println("verificationLink: " + link);
    String fullName = signUpRequest.getFirstName() + " " + signUpRequest.getLastName();
    emailSenderService.send(signUpRequest.getEmailId(), emailSenderService.buildEmail(fullName, link));

    signUpResponse.setSignUpDetails(savedSignUp);
    signUpResponse.setMessage("User successfully registered.");
    return signUpResponse;
  }
}
