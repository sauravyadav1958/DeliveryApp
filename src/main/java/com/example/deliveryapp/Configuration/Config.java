package com.example.deliveryapp.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application_${spring.profiles.active}.properties")
public class Config {

  @Value("${server.port}")
  private String port;

  @Value("${server.hostname}")
  private String hostName;

  @Value("${jwt.cookieExpiry}")
  private Long cookieExpiry;
  @Value("${Rzp_key_id}")
  private String Rzp_key_id;
  @Value("${Rzp_key_secret}")
  private String Rzp_key_secret;

  public String getPort() {
    return port;
  }

  public String getHostname() {
    return hostName;
  }

  public Long getCookieExpiry() {
    return cookieExpiry;
  }

  public String getRzp_key_id() {
    return Rzp_key_id;
  }

  public String getRzp_key_secret() {
    return Rzp_key_secret;
  }
}
