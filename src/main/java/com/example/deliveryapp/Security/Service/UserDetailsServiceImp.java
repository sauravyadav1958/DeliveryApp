package com.example.deliveryapp.Security.Service;

import com.example.deliveryapp.Security.Entity.SignUp;
import com.example.deliveryapp.Security.Repository.SignUpRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

  private final static String USER_NOT_FOUND_MSG = "user with email %s is not found.";

  private final SignUpRepository signUpRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    SignUp signUp = signUpRepository.findByEmailId(email);
    if (signUp == null) {
      new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
    }
    return signUp;
  }

}