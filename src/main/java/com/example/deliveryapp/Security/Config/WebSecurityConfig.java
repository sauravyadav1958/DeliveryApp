package com.example.deliveryapp.Security.Config;

import com.example.deliveryapp.Security.JwtAuthenticationEntryPoint;
import com.example.deliveryapp.Security.Service.UserDetailsServiceImp;
import com.example.deliveryapp.Security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private JwtAuthenticationEntryPoint point;

  @Autowired
  private JwtFilter jwtFilter;

  @Autowired
  private UserDetailsServiceImp userDetailsServiceImp;


  private static final String[] WHITE_LIST_URLS_ADMIN_ONLY = {

      "/admin/saveRestaurant/",

      "/updateRestaurant/",
      "/deleteRestaurant/",
  };
  private static final String[] WHITE_LIST_URLS_USER_ONLY = {

      "/placeOrder/",
      "/getOrder/",
      "/getAllOrders/",
      "/updateOrder/"

  };

  private static final String[] WHITE_LIST_URLS_ALL = {

      "/changePassword/",
      "/tokenVerify/",
      "/logout/",
      "/jwtLogout/"

  };

  private static final String[] WHITE_LIST_URLS_PUBLIC = {
      "/admin/signUp/**",
      // (applicable for antMatchers) here we don't have to put ** in the beginning since these are public URL hence not under hasRole() hence no restriction.
      "/user/signUp/**",
      "/confirm/**",
      "/resendConfirmation/**",
      "/login/**",
      "/resetPasswordLink/**",
      "/confirmResetPasswordLink/**",
      "/jwtLogin/**",
      "/refreshToken/**",
      "/getRestaurant/**",
      "/getAllRestaurants/**",
      "/restTemplate/**",
  };

  private static final String[] OPEN_API = {
      "/v3/api-docs/**",
      "/swagger-resources/**",
      "/configuration/ui/**",
      "/configuration/security/**",
      "/swagger-ui/**",
      "/webjars/**",
      "/swagger-ui.html/**"
  };


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

//  @Bean
//  public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//    UserDetails user = User.withUsername("user")
//        .password(passwordEncoder.encode("password"))
//        .roles("USER")
//        .build();
//
//    UserDetails admin = User.withUsername("admin")
//        .password(passwordEncoder.encode("admin"))
//        .roles("USER", "ADMIN")
//        .build();
//
//    return new InMemoryUserDetailsManager(user, admin);
//  }

// TODO how to handle all type of authentication

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {

    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // retrieves user information.

    provider.setPasswordEncoder(passwordEncoder());

    provider.setUserDetailsService(
        userDetailsServiceImp); // setting userDetails /*userDetailsServiceImp initialized? */

    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration builder)
      throws Exception {
    return builder.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .cors(
            cors -> cors.disable())
        .csrf(
            csrf -> csrf.disable())
        .authorizeHttpRequests(r -> r
            .requestMatchers(WHITE_LIST_URLS_ADMIN_ONLY)
            .hasRole("ADMIN")

            .requestMatchers(WHITE_LIST_URLS_USER_ONLY).hasRole("USER")
            .requestMatchers(WHITE_LIST_URLS_ALL).hasAnyRole("USER", "ADMIN")
            .requestMatchers(WHITE_LIST_URLS_PUBLIC).permitAll()
            .requestMatchers(OPEN_API).permitAll().anyRequest().authenticated())

        .exceptionHandling(ex -> ex.authenticationEntryPoint(
            point))
        .sessionManagement(
            s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.addFilterBefore(jwtFilter,
        UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return web -> web.ignoring().requestMatchers(
//        OPEN_API
//    ).requestMatchers(WHITE_LIST_URLS_PUBLIC);
//  }
}
