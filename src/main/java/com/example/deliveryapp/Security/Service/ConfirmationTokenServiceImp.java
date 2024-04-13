package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Security.Entity.ConfirmationToken;
import com.example.deliveryapp.Security.Repository.ConfirmationTokenRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.Service.email.EmailSenderService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfirmationTokenServiceImp implements ConfirmationTokenService {

  @Autowired
  ConfirmationTokenRepository confirmationTokenRepository;
  @Autowired
  SignUpRepository signUpRepository;
  @Autowired
  EmailSenderService emailSenderService;

  @Override
  @Transactional // will execute if the transaction is successful
  public String confirm(String token) {
    ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
    if (confirmationToken == null) {
      return "token not found.";
    } else if (confirmationToken.getConfirmedAt() != null) {
      return "email is already confirmed.";
    }

    LocalDateTime expiredAt = confirmationToken.getExpiresAt();
    if (expiredAt.isBefore(LocalDateTime.now())) {
      return "token is expired.";
    }

    confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    signUpRepository.enableUser(confirmationToken.getSignUp().getEmailId());
    return "Verified";
  }

  @Override
  public String resendConfirmation(String oldToken) {
    ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(oldToken);
    if (confirmationToken == null) {
      return "oldToken doesn't exist";
    }else if(confirmationToken.getConfirmedAt() != null){
      return "Account has already been verified";
    }
    String newToken = UUID.randomUUID().toString();
    confirmationToken.setToken(newToken);
    confirmationTokenRepository.save(confirmationToken);
    String link = "http://127.0.0.1:8080/confirm?token=" + newToken;
    String fullName =
        confirmationToken.getSignUp().getFirstName() + " " + confirmationToken.getSignUp()
            .getLastName();
    emailSenderService.send(confirmationToken.getSignUp().getEmailId(),
        emailSenderService.buildEmail(fullName, link));
    return newToken;

  }

}
