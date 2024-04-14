package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Configuration.Config;
import com.example.deliveryapp.Security.Entity.PasswordResetToken;
import com.example.deliveryapp.Security.Entity.SignUp;
import com.example.deliveryapp.Security.Model.ChangePasswordRequest;
import com.example.deliveryapp.Security.Model.ResetPasswordRequest;
import com.example.deliveryapp.Security.Repository.PasswordResetTokenRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.Service.email.EmailSenderService;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImp implements PasswordService {

  @Autowired
  SignUpRepository signUpRepository;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  EmailSenderService emailSenderService;
  @Autowired
  PasswordResetTokenRepository passwordResetTokenRepository;
  @Autowired
  Config config;

  @Override
  public String changePassword(ChangePasswordRequest changePasswordRequest) {
    SignUp signUp = signUpRepository.findByEmailId(changePasswordRequest.getEmailId());
    if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), signUp.getPassword())) {
      return "Password mismatch.";
    }
    signUp.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
    signUpRepository.save(signUp);
    return "Password has been successfully updated.";
  }

  @Override
  public String resetPasswordLink(String emailId) {
    SignUp signUp = signUpRepository.findByEmailId(emailId);
    if (signUp == null) {
      return "EmailId doesn't exist";
    }
    String token = UUID.randomUUID().toString();
    PasswordResetToken passwordResetToken = new PasswordResetToken(token, LocalDateTime.now(),
        LocalDateTime.now().plusMinutes(15));
    passwordResetToken.setSignUp(signUp);
    passwordResetTokenRepository.save(passwordResetToken);
    String link = "http://" + config.getHostname() + ":" + config.getPort()
        + "/confirmResetPasswordLink?token=" + token;
    String fullName = signUp.getFirstName() + " " + signUp.getLastName();
    emailSenderService.send(signUp.getEmailId(), emailSenderService.buildEmail(fullName, link));
    return link;
  }

  @Override
  public String confirmResetPasswordLink(String token, ResetPasswordRequest resetPasswordRequest) {
    PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
    if (passwordResetToken == null) {
      return "Link doesn't exist";
    } else if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
      return "Link has expired";
    }

    SignUp signUp = signUpRepository.findByEmailId(resetPasswordRequest.getEmailId());
    signUp.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
    signUpRepository.save(signUp);
    passwordResetToken.setResetDoneAt(LocalDateTime.now());
    passwordResetTokenRepository.save(passwordResetToken);

    return "Password reset has been done";
  }
}
