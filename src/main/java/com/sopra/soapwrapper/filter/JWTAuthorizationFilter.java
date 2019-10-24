package com.sopra.soapwrapper.filter;

import com.auth0.jwk.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Optional;

import static com.sopra.soapwrapper.configuration.SecurityConstants.HEADER_KEY;
import static com.sopra.soapwrapper.configuration.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
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

  private void authenticateWithToken(String token) {
    Optional.ofNullable(getAuthentication(token))
      .ifPresent(getContext()::setAuthentication);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(@NotNull String token) {
    Algorithm algorithm = getAlgorithm(token);

    // parse the token.
    String user = JWT.require(algorithm).build().verify(token).getSubject();

    if (user != null) {
      return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
    return null;

  }

  private Algorithm getAlgorithm(@NotNull String token) {
    DecodedJWT jwt = JWT.decode(token);
    JwkProvider provider = new UrlJwkProvider("https://dev-5u3jw31d.auth0.com/");
    Jwk jwk = null;
    try {
      jwk = provider.get(jwt.getKeyId());
    } catch (JwkException e) {
      e.printStackTrace();
    }
    Algorithm algorithm = null;
    try {
      algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
    } catch (InvalidPublicKeyException e) {
      e.printStackTrace();
    }
    return algorithm;
  }
}
