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
  // antMatchers URLs pattern
  // URL pattern : /** --> /book , /book/20 , /book/20/author
  // URL pattern : /* --> /book , /magazine
  // For the above URL patterns just / will not work.

  //requestMatchers URLs pattern (with jaxb-api dependency)
  // will have to give / at the end every url : /login/ --> /login will work, /login/ will not work (Authentication is required)
  // will have to give /** at the end every url : /login/** --> /login will work, /login/ will not work (Authentication is not required)
  private static final String[] WHITE_LIST_URLS_ADMIN_ONLY = {

      "/admin/saveRestaurant/",
      // (applicable for antMatchers) here we have to specify ** at the beginning also if there is nothing after last / in the api being called and the api is under hasRole().
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
      AuthenticationConfiguration builder) // use for authentication of the user while login.
      throws Exception {
    return builder.getAuthenticationManager();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

/*
     CORS (Cross-Origin Resource Sharing)
     CSRF (Cross-Site Request Forgery)

     CSRF is a type of attack,
     and CSRF protection mechanisms are used to prevent it,
     while CORS is a security feature that controls
     how web browsers can access resources from different origins.
*/
    http
        .cors(
            cors -> cors.disable())
        .csrf(
            csrf -> csrf.disable())// If service is for non-browser clients we can disable CSRF protection.
        .authorizeHttpRequests(r -> r
            .requestMatchers(WHITE_LIST_URLS_ADMIN_ONLY)
            .hasRole("ADMIN") //applications where roles are sufficient to manage authorization
            //.antMatchers(WHITE_LIST_URLS_ADMIN_ONLY).hasAuthority("ADMIN") //applications where authorization is complex and dynamic
            .requestMatchers(WHITE_LIST_URLS_USER_ONLY).hasRole("USER")
            .requestMatchers(WHITE_LIST_URLS_ALL).hasAnyRole("USER", "ADMIN")
            .requestMatchers(WHITE_LIST_URLS_PUBLIC).permitAll()
            .requestMatchers(OPEN_API).permitAll().anyRequest().authenticated())
        /*
         1) Basic Auth : uses header which is base64 encoding of the username and password joined by a single colon.
         2) Format -> Authorization: Basic Base64-encoded(username:password)
         3) No cookies, hence no session or logging out a user
         4) Each request has to carry that header in order to be authenticated
        */
//        .httpBasic(Customizer.withDefaults())
        /*
        1) Uses HTML form for UserName and Password
        2) The server validates the credentials provided and creates a “session” in cookie having unique token.
           If session is deleted, user will be logged out.
        */
//        .formLogin(Customizer.withDefaults())
        .exceptionHandling(ex -> ex.authenticationEntryPoint(
            point)) //For handling exception for unauthenticated users
        .sessionManagement(
            s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // no session is created
    http.addFilterBefore(jwtFilter,
        UsernamePasswordAuthenticationFilter.class); // filter before specified class

    return http.build();
  }

//  @Bean
//  public WebSecurityCustomizer webSecurityCustomizer() {
//    return web -> web.ignoring().requestMatchers(
//        OPEN_API
//    ).requestMatchers(WHITE_LIST_URLS_PUBLIC);
//  }
}
