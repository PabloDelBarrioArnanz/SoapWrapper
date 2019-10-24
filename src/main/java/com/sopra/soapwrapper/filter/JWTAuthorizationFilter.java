package com.sopra.soapwrapper.filter;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.Optional;

import static com.sopra.soapwrapper.configuration.SecurityConstants.HEADER_KEY;
import static com.sopra.soapwrapper.configuration.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
  
  private final String issuerDomain;
  
  public JWTAuthorizationFilter(AuthenticationManager authManager, String issuerDomain) {
    super(authManager);
    this.issuerDomain = issuerDomain;
  }
  
  @Override
  protected void doFilterInternal(HttpServletRequest req,
                                  HttpServletResponse res,
                                  FilterChain chain) throws IOException, ServletException {
    Optional.ofNullable(req.getHeader(HEADER_KEY))
      .filter(header -> header.startsWith(TOKEN_PREFIX))
      .map(token -> token.replace(TOKEN_PREFIX, ""))
      .ifPresent(this::authenticateWithToken);
    chain.doFilter(req, res);
  }
  
  private void authenticateWithToken(@NotNull String token) {
    JWTVerifier jwtVerifier = buildJWTVerifier(token);
    Optional.ofNullable(PreAuthenticatedAuthenticationJsonWebToken.usingToken(token))
      .map(preAuth -> preAuth.verify(jwtVerifier))
      .ifPresent(getContext()::setAuthentication);
  }
  
  private JWTVerifier buildJWTVerifier(@NotNull String token) {
    return JWT.require(getAlgorithm(token)).build();
  }
  
  private Algorithm getAlgorithm(@NotNull String token) {
    return Optional.ofNullable(buildJwk(token))
      .map(this::getPublicKeyFromJwk)
      .map(publicKey -> Algorithm.RSA256(publicKey, null))
      .get();
  }
  
  private RSAPublicKey getPublicKeyFromJwk(Jwk jwk) {
    try {
      return (RSAPublicKey) jwk.getPublicKey();
    } catch (InvalidPublicKeyException e) {
      e.printStackTrace();
      return null;
    }
  }
  
  private Jwk buildJwk(@NotNull String token) {
    DecodedJWT jwt = JWT.decode(token);
    JwkProvider provider = new UrlJwkProvider(issuerDomain);
    try {
      return provider.get(jwt.getKeyId());
    } catch (JwkException e) {
      e.printStackTrace();
      return null;
    }
  }
}
