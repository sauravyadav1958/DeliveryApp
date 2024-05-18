package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Security.Entity.Login;
import com.example.deliveryapp.Security.Entity.SignUp;
import com.example.deliveryapp.Security.Entity.UserSession;
import com.example.deliveryapp.Security.Model.JwtRequest;
import com.example.deliveryapp.Security.Model.JwtResponse;
import com.example.deliveryapp.Security.Model.LogInRequest;
import com.example.deliveryapp.Security.Model.RefreshTokenRequest;
import com.example.deliveryapp.Security.Repository.LoginRepository;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import com.example.deliveryapp.Security.Repository.UserSessionRepository;
import com.example.deliveryapp.Security.utility.JWTUtility;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserDetailsServiceImp userDetailsServiceImp;
  @Autowired
  private RefreshTokenService refreshTokenService;
  @Autowired
  private JWTUtility jwtUtility;

  @Override
  public String login(LogInRequest loginInRequest, HttpServletResponse response) {
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

  @Override
  public JwtResponse jwtLogin(JwtRequest jwtRequest, HttpServletResponse response)
      throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              jwtRequest.getUsername(),
              jwtRequest.getPassword()
          )
      );
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }

    final UserDetails userDetails
        = userDetailsServiceImp.loadUserByUsername(jwtRequest.getUsername());

    final String accessToken =
        jwtUtility.generateToken(userDetails);

    Cookie cookie = new Cookie("accessToken", accessToken);
    cookie.setMaxAge(1 * 24 * 60 * 60); // expires in 7 days
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);

    final Date expiresAt = jwtUtility.getExpirationDateFromToken(accessToken);
    final String refreshToken = jwtUtility.generateRefreshToken(userDetails);
    final Date refreshExpiresAtToken = jwtUtility.getExpirationDateFromToken(refreshToken);
    final String tokenType = "Bearer";

    refreshTokenService.createRefreshToken(refreshToken);

    return new JwtResponse(accessToken, expiresAt, refreshToken, refreshExpiresAtToken, tokenType);
  }

  @Override
  public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest,
      HttpServletResponse response) {
    Date refreshTokenExpiryDate = jwtUtility.getExpirationDateFromToken(
        refreshTokenRequest.getRefreshToken());
    if (refreshTokenExpiryDate.before(Date.from(Calendar.getInstance().toInstant()))) {
      refreshTokenService.delete(refreshTokenRequest.getRefreshToken());
      throw new RuntimeException(
          refreshTokenRequest.getRefreshToken()
              + " Refresh token is expired. Please make a new login..!");
    }

    String userName = jwtUtility.getUsernameFromToken(refreshTokenRequest.getRefreshToken());
    final UserDetails userDetails
        = userDetailsServiceImp.loadUserByUsername(userName);
    final String accessToken =
        jwtUtility.generateToken(userDetails);

    Cookie cookie = new Cookie("accessToken", accessToken);
    cookie.setMaxAge(1 * 24 * 60 * 60); // expires in 7 days
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);

    final Date expiresAt = jwtUtility.getExpirationDateFromToken(accessToken);
    final String tokenType = "Bearer";

    return new JwtResponse(accessToken, expiresAt, refreshTokenRequest.getRefreshToken(),
        refreshTokenExpiryDate, tokenType);
  }

  @Override
  public String jwtLogout(HttpServletResponse response) { // not deleting cookie
    Cookie cookie = new Cookie("accessToken", null);
    cookie.setMaxAge(0); // expires in 7 days
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    response.addCookie(cookie);
    return "loggedOut";
  }
}
