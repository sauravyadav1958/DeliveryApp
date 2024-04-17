package com.example.deliveryapp.Security.Controller;

import com.example.deliveryapp.Security.Enums.UserRole;
import com.example.deliveryapp.Security.Model.ChangePasswordRequest;
import com.example.deliveryapp.Security.Model.JwtRequest;
import com.example.deliveryapp.Security.Model.JwtResponse;
import com.example.deliveryapp.Security.Model.LogInRequest;
import com.example.deliveryapp.Security.Model.ResetPasswordRequest;
import com.example.deliveryapp.Security.Model.SignUpRequest;
import com.example.deliveryapp.Security.Model.SignUpResponse;
import com.example.deliveryapp.Security.Service.ConfirmationTokenService;
import com.example.deliveryapp.Security.Service.LoginService;
import com.example.deliveryapp.Security.Service.PasswordService;
import com.example.deliveryapp.Security.Service.SignUpService;
import com.example.deliveryapp.Security.Service.UserDetailsServiceImp;
import com.example.deliveryapp.Security.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class SecurityController {

  @Autowired
  SignUpService signUpService;


  @Autowired
  ConfirmationTokenService confirmationTokenService;

  @Autowired
  LoginService loginService;


  @Autowired
  PasswordService passwordService;

  @Autowired
  private JWTUtility jwtUtility;

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserDetailsServiceImp userDetailsServiceImp;

  @GetMapping("/")
  public String home() {
    return "Welcome to Daily Code Buffer!!";
  }

  @PostMapping("token/login")
  public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

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

    final String token =
        jwtUtility.generateToken(userDetails);

    return new JwtResponse(token);
  }


  @PostMapping("/admin/signUp")
  public ResponseEntity<SignUpResponse> adminSignUp(@RequestBody SignUpRequest signUpRequest) {
    signUpRequest.setRole(UserRole.ADMIN);
    SignUpResponse signUpResponse = signUpService.signUp(signUpRequest);

    return new ResponseEntity<SignUpResponse>(signUpResponse, HttpStatus.CREATED);
  }

  @PostMapping("/user/signUp")
  public ResponseEntity<SignUpResponse> userSignUp(@RequestBody SignUpRequest signUpRequest) {
    signUpRequest.setRole(UserRole.USER);
    SignUpResponse signUpResponse = signUpService.signUp(signUpRequest);

    return new ResponseEntity<SignUpResponse>(signUpResponse, HttpStatus.CREATED);
  }

  @GetMapping("/confirm")
  public String confirm(@RequestParam String token) {
    String message = confirmationTokenService.confirm(token);

    return message;

  }

  @GetMapping("/resendConfirmation")
  public String resendConfirmation(@RequestParam String oldToken) {
    String message = confirmationTokenService.resendConfirmation(oldToken);

    return message;

  }

  @PostMapping("/login")
  public String login(@RequestBody LogInRequest loginInRequest) {

    String userSession = loginService.login(loginInRequest);

    return userSession;
  }

  @PostMapping("/logout/{userId}")
  public String logout(@PathVariable Long userId) {

    String logoutResult = loginService.logout(userId);
    return logoutResult;

  }

  @PostMapping("/changePassword")
  public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {

    String changePasswordResult = passwordService.changePassword(changePasswordRequest);
    return changePasswordResult;

  }

  @PostMapping("/resetPasswordLink")
  public String resetPasswordLink(@RequestBody ResetPasswordRequest resetPasswordRequest) {

    String resetPasswordResult = passwordService.resetPasswordLink(
        resetPasswordRequest.getEmailId());
    return resetPasswordResult;

  }

  @PostMapping("/confirmResetPasswordLink")
  public String confirmResetPasswordLink(@RequestParam String token,
      @RequestBody ResetPasswordRequest resetPasswordRequest) {

    String resetPasswordResult = passwordService.confirmResetPasswordLink(token,
        resetPasswordRequest);
    return resetPasswordResult;

  }


}
