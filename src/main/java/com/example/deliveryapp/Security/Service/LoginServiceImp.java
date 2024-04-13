package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Security.Entity.Login;
import com.example.deliveryapp.Security.Entity.SignUp;
import com.example.deliveryapp.Security.Entity.UserSession;
import com.example.deliveryapp.Security.Model.LogInRequest;
import com.example.deliveryapp.Security.Repository.LoginRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.Repository.UserSessionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginServiceImp implements LoginService {

  @Autowired
  SignUpRepository signUpRepository;
  @Autowired
  LoginRepository loginRepository;

  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  UserSessionRepository userSessionRepository;

  @Override
  public String login(LogInRequest loginInRequest) {
    SignUp signUpExisting = signUpRepository.findByEmailId(loginInRequest.getEmailId());
    if (signUpExisting == null) {
      return "User doesn't exist";
    } else if (!signUpExisting.isEnabled()) {
      return "Kindly verify the account first";
    }

    Optional<Login> loginExisting = loginRepository.findById(
        signUpExisting.getUserId());
    if (loginExisting.isPresent()) {
      return "User already loggedIn";
    }
    if (signUpExisting.getEmailId().equals(loginInRequest.getEmailId())
        && passwordEncoder.matches(loginInRequest.getPassword(),
        signUpExisting.getPassword())) {
      String randomUUID = UUID.randomUUID().toString();
      log.debug("randomUUID: {}", randomUUID);
      UserSession userSession = new UserSession(signUpExisting.getUserId(), randomUUID,
          LocalDateTime.now());
      Login login = new Login(signUpExisting.getUserId(), loginInRequest.getEmailId(),
          loginInRequest.getPassword());
      UserSession savedUserSession = userSessionRepository.save(userSession);
      Login savedLogin = loginRepository.save(login);
      return savedUserSession.toString();
    }
    return "wrong credentials";
  }

  @Override
  public String logout(Long userId) {
    Optional<UserSession> userSession = userSessionRepository.findByUserId(userId);
    if (userSession.isEmpty()) {
      return "User already loggedOut";
    }

    Optional<Login> login = loginRepository.findById(
        userId);

    userSessionRepository.delete(userSession.get());
    loginRepository.delete(login.get());

    return "loggedOut";
  }
}
