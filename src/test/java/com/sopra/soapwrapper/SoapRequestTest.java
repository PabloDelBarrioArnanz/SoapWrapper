package com.sopra.soapwrapper;

import com.sopra.soapwrapper.controller.CalculatorController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.support.SourceAssertionError;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SoapRequestTest {

  @Autowired private WebServiceGatewaySupport webServiceGatewaySupport;

  @Autowired private CalculatorController calculatorController;

  private MockWebServiceServer mockWebServiceServer;

  @Before
  public void setUp() {
    mockWebServiceServer = MockWebServiceServer.createServer(webServiceGatewaySupport);
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void addShouldReturn3WhenCalledWith1And2() {
    Source responsePayload = new StringSource(
      "<AddResponse xmlns=\"http://tempuri.org/\">" +
        "<AddResult>3</AddResult>" +
        "</AddResponse>"
    );

    Source requestPayload = new StringSource(
      "<ns2:Add xmlns:ns2=\"http://tempuri.org/\">" +
        "<ns2:intA>1</ns2:intA>" +
        "<ns2:intB>2</ns2:intB>" +
        "</ns2:Add>"
    );

    mockWebServiceServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.add(1, 2).getBody(), 3);
    mockWebServiceServer.verify();
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void multiplyShouldReturn2WhenCalledWith1And2() {
    Source responsePayload = new StringSource(
      "<MultiplyResponse xmlns=\"http://tempuri.org/\">" +
        "<MultiplyResult>2</MultiplyResult>" +
        "</MultiplyResponse>"
    );

    Source requestPayload = new StringSource(
      "<ns2:Multiply xmlns:ns2=\"http://tempuri.org/\">" +
        "<ns2:intA>1</ns2:intA>" +
        "<ns2:intB>2</ns2:intB>" +
        "</ns2:Multiply>"
    );

    mockWebServiceServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.multiply(1, 2).getBody(), 2);
    mockWebServiceServer.verify();
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void subtractShouldReturn1WhenCalledWith2And1() {
    Source responsePayload = new StringSource(
      "<SubtractResponse xmlns=\"http://tempuri.org/\">" +
        "<SubtractResult>1</SubtractResult>" +
        "</SubtractResponse>"
    );

    Source requestPayload = new StringSource(
      "<ns2:Subtract xmlns:ns2=\"http://tempuri.org/\">" +
        "<ns2:intA>2</ns2:intA>" +
        "<ns2:intB>1</ns2:intB>" +
        "</ns2:Subtract>"
    );

    mockWebServiceServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.subtract(2, 1).getBody(), 1);
    mockWebServiceServer.verify();
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void divideShouldReturn2WhenCalledWith2And1() {
    Source responsePayload = new StringSource(
      "<DivideResponse xmlns=\"http://tempuri.org/\">" +
        "<DivideResult>2</DivideResult>" +
        "</DivideResponse>"
    );

    Source requestPayload = new StringSource(
      "<ns2:Divide xmlns:ns2=\"http://tempuri.org/\">" +
        "<ns2:intA>2</ns2:intA>" +
        "<ns2:intB>1</ns2:intB>" +
        "</ns2:Divide>"
    );

    mockWebServiceServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.divide(2, 1).getBody(), 2);
    mockWebServiceServer.verify();
  }

  @Test(expected = SourceAssertionError.class)
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void addShouldFailWhenGivenBadRequest() {
    Source requestBadPayload = new StringSource(
      "<ns2:Add xmlns:ns2=\"http://tempuri.org/\">" +
        "<ns2:floatA>1</ns2:floatA>" +
        "<ns2:intB>2</ns2:intB>" +
        "</ns2:Add>"
    );

    Source responsePayload = new StringSource(
      "<AddResponse xmlns=\"http://tempuri.org/\">" +
        "<AddResultado>3</AddResultado>" +
        "</AddResponse>"
    );

    mockWebServiceServer.expect(payload(requestBadPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.add(1, 2).getBody(), 3);
    mockWebServiceServer.verify();
  }

  @Test(expected = SourceAssertionError.class)
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void subtractShouldFailWhenGivenBadRequest() {
    Source requestBadPayload = new StringSource(
      "<ns2:Subtract xmlns:ns2=\"http://tempuri.org/\">" +
        "<ns2:floatA>1</ns2:floatA>" +
        "<ns2:intB>2</ns2:intB>" +
        "</ns2:Subtract>"
    );

    Source responsePayload = new StringSource(
      "<SubtractResponse xmlns=\"http://tempuri.org/\">" +
        "<AddResultado>3</AddResultado>" +
        "</SubtractResponse>"
    );

    mockWebServiceServer.expect(payload(requestBadPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.subtract(1, 2).getBody(), 3);
    mockWebServiceServer.verify();
  }

  @Test(expected = SourceAssertionError.class)
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void multiplyShouldFailWhenGivenBadRequest() {
    Source requestBadPayload = new StringSource(
      "<ns2:Subtract xmlns:ns2=\"http://tempuri.org/\">" +
        "<ns2:floatA>1</ns2:floatA>" +
        "<ns2:intB>2</ns2:intB>" +
        "</ns2:Subtract>"
    );

    Source responsePayload = new StringSource(
      "<SubtractResponse xmlns=\"http://tempuri.org/\">" +
        "<AddResultado>3</AddResultado>" +
        "</SubtractResponse>"
    );

    mockWebServiceServer.expect(payload(requestBadPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.multiply(1, 2).getBody(), 3);
    mockWebServiceServer.verify();
  }

  @Test(expected = SourceAssertionError.class)
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void divideShouldFailWhenGivenBadRequest() {
    Source requestBadPayload = new StringSource(
        "<ns2:Divide xmlns:ns2=\"http://tempuri.org/\">" +
          "<ns2:floatA>1</ns2:floatA>" +
          "<ns2:intB>2</ns2:intB>" +
          "</ns2:Divide>"
    );

    Source responsePayload = new StringSource(
      "<DivideResponse xmlns=\"http://tempuri.org/\">" +
        "<AddResultado>3</AddResultado>" +
        "</DivideResponse>"
    );

    mockWebServiceServer.expect(payload(requestBadPayload)).andRespond(withPayload(responsePayload));
    assertEquals(calculatorController.divide(1, 2).getBody(), 3);
    mockWebServiceServer.verify();
  }
}
