package com.sopra.soapwrapper;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenParser {
  
  @JsonProperty("access_token") private String accessToken;
  @JsonProperty("expires_in") private long expiresIn;
  @JsonProperty("token_type") private String tokenType;
  
  public String getAccessToken() {
    return accessToken;
  }
  
  public long getExpiresIn() {
    return expiresIn;
  }
  
  public String getTokenType() {
    return tokenType;
  }
}
