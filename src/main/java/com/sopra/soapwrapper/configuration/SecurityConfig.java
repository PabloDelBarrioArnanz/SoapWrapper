package com.sopra.soapwrapper.configuration;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import com.sopra.soapwrapper.filter.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Value(value = "${auth0.apiAudience}") private String apiAudience;
  @Value(value = "${auth0.issuer}") private String issuer;
  @Autowired private AuthenticationProvider authenticationProvider;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtWebSecurityConfigurer.forRS256(apiAudience, issuer)
      .configure(http)
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeRequests()
      .anyRequest()
      .authenticated()
      .and()
      .addFilter(new JWTAuthorizationFilter(authenticationManager(), authenticationProvider));
  }
}