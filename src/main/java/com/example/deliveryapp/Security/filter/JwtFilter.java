package com.example.deliveryapp.Security.filter;

import com.example.deliveryapp.Security.Service.UserDetailsServiceImp;
import com.example.deliveryapp.Security.utility.JWTUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter { // executed once only for a request

  @Autowired
  private JWTUtility jwtUtility;

  @Autowired
  private UserDetailsServiceImp userDetailsServiceImp;


  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = httpServletRequest.getHeader("Authorization");
    String token = null;
    String userName = null;

    if (httpServletRequest.getCookies() != null) {
      for (Cookie cookie : httpServletRequest.getCookies()) {
        if (cookie.getName().equals("accessToken")) {
          token = cookie.getValue();
          userName = jwtUtility.getUsernameFromToken(token);
        }
      }
    } else if (null != authorization && authorization.startsWith("Bearer ")) {
      token = authorization.substring(7);

      try {

        userName = jwtUtility.getUsernameFromToken(token);

      } catch (IllegalArgumentException e) {
        logger.info("Illegal Argument while fetching the username !!");
        e.printStackTrace();
      } catch (ExpiredJwtException e) {
        logger.info("Given jwt token is expired !!");
        e.printStackTrace();
      } catch (MalformedJwtException e) {
        logger.info("Some changed has done in token !! Invalid Token");
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();

      }

    } else {
      logger.info("Invalid Header Value !! ");
    }

    if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) { // checking if authentication is set, if not, it is considered unauthenticated
      UserDetails userDetails
          = userDetailsServiceImp.loadUserByUsername(userName);

      // UsernamePasswordAuthenticationToken implements authentication interface, simple presentation of a username and password.
      // being used later for setting authentication.
      if (jwtUtility.validateToken(token, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
            = new UsernamePasswordAuthenticationToken(userDetails,
            null, userDetails.getAuthorities());

        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest) //add additional request-related information, eg:client’s IP address
        );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } else {
        logger.info("Validation fails !!");
      }

    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

}
