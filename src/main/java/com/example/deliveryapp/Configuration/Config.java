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

  public String getPort() {
    return port;
  }

  public String getHostname() {
    return hostName;
  }
  public Long getCookieExpiry() {
    return cookieExpiry;
  }


}
