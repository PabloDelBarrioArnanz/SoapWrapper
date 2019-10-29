package com.sopra.soapwrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SecurityTest {
  
  private static final String EXPIRED_TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IlJqSkRNekk0TVVZMVF6ZE" +
    "NORFZCUVRJek1VRTROVFUxTTBNMU9VTXpRMEUyTURnMk0wWkVSZyJ9.eyJpc3MiOiJodHRwczovL2Rldi01dTNqdzMxZC5hdXRoMC5jb20vIiwic3Vi" +
    "IjoiMXlXdjM1aE43WmVLTzYyYW9VbDJiNmRkMHBUbFpCQzhAY2xpZW50cyIsImF1ZCI6Imh0dHA6Ly9qd3QtdGVzdC5jb20iLCJpYXQiOjE1NzE4MzM" +
    "0NDYsImV4cCI6MTU3MTkxOTg0NiwiYXpwIjoiMXlXdjM1aE43WmVLTzYyYW9VbDJiNmRkMHBUbFpCQzgiLCJndHkiOiJjbGllbnQtY3JlZGVudGlhbH" +
    "MifQ.mE05IQocK_14T0OjqcLTVuLZiv2A-SJoT97gBsbQBt6BkiFnoI3n6P6_1yVOKQcT1qioFVDpV0rNLD2U1hUVjfKNanSw7SfptrNLRrovl-E_H-" +
    "Si-ppAC7ApZgdVJhhkXYAj2yGKQNrOwNkpRcxuTys4MaEL4VJHOBDPBlSH4QFgP4PlplOk1Wl1CRyAV1AqqlHOa6R6Ad9VR9mthq38ByfoI3yhEF3r4" +
    "VpY8oUl8sDvTimXk4uKI7Dqf8jAM4MKSLnKkGhTSuiiPIrk5k1HkycV9hObc_Tpyac78hc5p8ek4qrsttGzJb00CYLeJ_qlN9dwAF5MupcjUi-DmQzMqA";
  
  private static final String APP_CONTEXT = "/calculator";
  private static final String ADD_OPERATION = "/add";
  private static final String SUBTRACT_OPERATION = "/subtract";
  private static final String MULTIPLY_OPERATION = "/multiply";
  private static final String DIVIDE_OPERATION = "/divide";
  private static final String REQUEST_PARAMETERS = "?numberA=1&numberB=1";
  
  @Autowired private TestRestTemplate testRestTemplate;
  @Autowired private ObjectMapper objectMapper;
  
  @Test
  public void whenHeaderIsEmpty_addOperation_unauthorizedAccess() {
    ResponseEntity<String> response = testRestTemplate.getForEntity(buildAddOperationUrl(), String.class);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenHeaderIsEmpty_subtractOperation_unauthorizedAccess() {
    ResponseEntity<String> response = testRestTemplate.getForEntity(buildSubtractOperationUrl(), String.class);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenHeaderIsEmpty_multiplyOperation_unauthorizedAccess() {
    ResponseEntity<String> response = testRestTemplate.getForEntity(buildMultiplyOperationUrl(), String.class);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenHeaderIsEmpty_divideOperation_unauthorizedAccess() {
    ResponseEntity<String> response = testRestTemplate.getForEntity(buildDivideOperationUrl(), String.class);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsWrong_addOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildAddOperationUrl(),"Bearer XXXX");
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsWrong_subtractOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildSubtractOperationUrl(),"Bearer XXXX");
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsWrong_multiplyOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildMultiplyOperationUrl(),"Bearer XXXX");
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsWrong_divideOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildDivideOperationUrl(),"Bearer XXXX");
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsValidButIsExpired_addOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildAddOperationUrl(), EXPIRED_TOKEN);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsValidButIsExpired_subtractOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildSubtractOperationUrl(), EXPIRED_TOKEN);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsValidButIsExpired_multiplyOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildSubtractOperationUrl(), EXPIRED_TOKEN);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsValidButIsExpired_divideOperation_unauthorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildDivideOperationUrl(), EXPIRED_TOKEN);
    assertThat(response.getStatusCode(), equalTo(UNAUTHORIZED));
  }
  
  @Test
  public void whenTokenIsValid_addOperation_authorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildAddOperationUrl(), getToken());
    assertThat(response.getStatusCode(), equalTo(OK));
  }
  
  @Test
  public void whenTokenIsValid_subtractOperation_authorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildSubtractOperationUrl(), getToken());
    assertThat(response.getStatusCode(), equalTo(OK));
  }
  
  @Test
  public void whenTokenIsValid_multiplyOperation_authorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildMultiplyOperationUrl(), getToken());
    assertThat(response.getStatusCode(), equalTo(OK));
  }
  
  @Test
  public void whenTokenIsValid_divideOperation_authorizedAccess() {
    ResponseEntity<String> response = getForEntityWithHeaders(buildDivideOperationUrl(), getToken());
    assertThat(response.getStatusCode(), equalTo(OK));
  }
  
  private String buildAddOperationUrl() {
    return APP_CONTEXT + ADD_OPERATION + REQUEST_PARAMETERS;
  }
  
  private String buildSubtractOperationUrl() {
    return APP_CONTEXT + SUBTRACT_OPERATION + REQUEST_PARAMETERS;
  }
  
  private String buildMultiplyOperationUrl() {
    return APP_CONTEXT + MULTIPLY_OPERATION + REQUEST_PARAMETERS;
  }
  
  private String buildDivideOperationUrl() {
    return APP_CONTEXT + DIVIDE_OPERATION + REQUEST_PARAMETERS;
  }
  
  private ResponseEntity<String> getForEntityWithHeaders(String url, String token) {
    return testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(buildAuthorizationHeader(token)), String.class);
  }
  
  private HttpHeaders buildAuthorizationHeader(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", token);
    return headers;
  }
  
  private String getToken() {
    try {
      HttpResponse<String> response = Unirest.post("https://dev-5u3jw31d.auth0.com/oauth/token")
        .header("content-type", "application/json")
        .body("{\"client_id\":\"1yWv35hN7ZeKO62aoUl2b6dd0pTlZBC8\",\"client_secret\":\"yBlKWYNwuO4GaejC-cG7YnhebkMbN00Fk9MdLDlvh0raCU2bIYV9WddJlQj9QPgZ\",\"audience\":\"http://jwt-test.com\",\"grant_type\":\"client_credentials\"}")
        .asString();
      TokenParser tokenParser = objectMapper.readValue(response.getBody(), TokenParser.class);
      return tokenParser.getTokenType() + " " + tokenParser.getAccessToken();
    } catch (UnirestException | JsonProcessingException e) {
      e.printStackTrace();
      return EXPIRED_TOKEN;
    }
  }
}
