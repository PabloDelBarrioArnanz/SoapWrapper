package com.sopra.soapwrapper.filter;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.spring.security.api.JwtAuthenticationProvider;
import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.sopra.soapwrapper.configuration.SecurityConstants.HEADER_KEY;
import static com.sopra.soapwrapper.configuration.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  
  private AuthenticationProvider authenticationProvider;
  
  public JWTAuthorizationFilter(AuthenticationManager authManager, AuthenticationProvider authenticationProvider) {
    super(authManager);
    this.authenticationProvider = authenticationProvider;
  }
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse result, FilterChain chain) throws IOException, ServletException {
    Optional.ofNullable(request.getHeader(HEADER_KEY))
      .filter(header -> header.startsWith(TOKEN_PREFIX))
      .map(token -> token.replace(TOKEN_PREFIX, ""))
      .map(PreAuthenticatedAuthenticationJsonWebToken::usingToken)
      .map(this::authenticate)
      .ifPresent(getContext()::setAuthentication);
    chain.doFilter(request, result);
  }
  
  private Authentication authenticate(PreAuthenticatedAuthenticationJsonWebToken preAuthenticated) {
    try {
      return authenticationProvider.authenticate(preAuthenticated);
    } catch (BadCredentialsException ex) {
      return null;
    }
  }
}
