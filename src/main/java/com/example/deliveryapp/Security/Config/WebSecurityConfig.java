package com.example.deliveryapp.Security.Config;

import com.example.deliveryapp.Security.JwtAuthenticationEntryPoint;
import com.example.deliveryapp.Security.Service.UserDetailsServiceImp;
import com.example.deliveryapp.Security.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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


  private final UserDetailsServiceImp userDetailsServiceImp;
  private static final String[] WHITE_LIST_URLS = {
      "/saveRestaurant",
      "/updateRestaurant",
  };

  public WebSecurityConfig(UserDetailsServiceImp userDetailsServiceImp) {
    this.userDetailsServiceImp = userDetailsServiceImp;
  }


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
  public AuthenticationManager authenticationManager(AuthenticationConfiguration builder)
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
            csrf -> csrf.disable()) // If service is for non-browser clients we can disable CSRF protection.
        .authorizeRequests().antMatchers("WHITE_LIST_URLS").hasRole("ADMIN")
        .antMatchers("*/user*", "/getOrder", "/updateOrder", "*/getAllOrders*")
        .hasRole("USER").anyRequest().authenticated().and()
        /*
         1) Basic Auth : uses header which is base64 encoding of the username and password joined by a single colon.
         2) Format -> Authorization: Basic Base64-encoded(username:password)
         3) No cookies, hence no session or logging out a user
         4) Each request has to carry that header in order to be authenticated
        */
        .httpBasic(Customizer.withDefaults())
        /*
        1) Uses HTML form for UserName and Password
        2) The server validates the credentials provided and creates a “session” in cookie having unique token.
           If session is deleted, user will be logged out.
        */
        .formLogin(Customizer.withDefaults())
        .logout(logout -> logout.permitAll())
        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
        .sessionManagement().
        sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//        .oauth2Login(oauth2login ->
//            oauth2login.loginPage("/oauth2/authorization/api-client-oidc"))
//        .oauth2Client(Customizer.withDefaults());

    return http.build();
  }
}
