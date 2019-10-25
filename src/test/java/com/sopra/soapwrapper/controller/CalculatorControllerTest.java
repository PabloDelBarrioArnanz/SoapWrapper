package com.sopra.soapwrapper.controller;

import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
import com.auth0.spring.security.api.authentication.PreAuthenticatedAuthenticationJsonWebToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CalculatorControllerTest {
  
  private static final String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlJqSkRNekk0TVVZMVF6ZENORFZCUVRJek1VRTROVFUxTTBNMU9VTXpRMEUyTURnMk0wWkVSZyJ9.eyJpc3MiOiJodHRwczovL2Rldi01dTNqdzMxZC5hdXRoMC5jb20vIiwic3ViIjoiMXlXdjM1aE43WmVLTzYyYW9VbDJiNmRkMHBUbFpCQzhAY2xpZW50cyIsImF1ZCI6Imh0dHA6Ly9qd3QtdGVzdC5jb20iLCJpYXQiOjE1NzE4MzM0NDYsImV4cCI6MTU3MTkxOTg0NiwiYXpwIjoiMXlXdjM1aE43WmVLTzYyYW9VbDJiNmRkMHBUbFpCQzgiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbHMifQ.mE05IQocK_14T0OjqcLTVuLZiv2A-SJoT97gBsbQBt6BkiFnoI3n6P6_1yVOKQcT1qioFVDpV0rNLD2U1hUVjfKNanSw7SfptrNLRrovl-E_H-Si-ppAC7ApZgdVJhhkXYAj2yGKQNrOwNkpRcxuTys4MaEL4VJHOBDPBlSH4QFgP4PlplOk1Wl1CRyAV1AqqlHOa6R6Ad9VR9mthq38ByfoI3yhEF3r4VpY8oUl8sDvTimXk4uKI7Dqf8jAM4MKSLnKkGhTSuiiPIrk5k1HkycV9hObc_Tpyac78hc5p8ek4qrsttGzJb00CYLeJ_qlN9dwAF5MupcjUi-DmQzMqA";
  
  @Autowired private TestRestTemplate testRestTemplate;
  
  @Test
  public void whenHeaderIsEmpty_unauthorizedAccess() {
    ResponseEntity<String> response = testRestTemplate.getForEntity("/calculator/add?numberA=1&numberB=1", String.class);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsWrong_unauthorizedAccess() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer XXXX");
    ResponseEntity<String> response = testRestTemplate.getForEntity("/calculator/add?numberA=1&numberB=1", String.class, headers);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsValidButIsExpired_unauthorizedAccess() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", token);
    ResponseEntity<String> response = testRestTemplate.getForEntity("/calculator/add?numberA=1&numberB=1", String.class, headers);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
}
