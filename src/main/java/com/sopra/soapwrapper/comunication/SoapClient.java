package com.sopra.soapwrapper.comunication;

import com.calculator.wsdl.*;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.Optional;

public class SoapClient extends WebServiceGatewaySupport {

  private static final String URI = "http://www.dneonline.com/calculator.asmx";
  private static final String ADD_ACTION = "http://tempuri.org/Add";
  private static final String SUBTRACT_ACTION = "http://tempuri.org/Subtract";
  private static final String MULTIPLY_ACTION = "http://tempuri.org/Multiply";
  private static final String DIVIDE_ACTION = "http://tempuri.org/Divide";


  public AddResponse add(int numberA, int numberB) {
    Add request = new Add();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, ADD_ACTION, AddResponse.class);
  }

  public SubtractResponse subtract(int numberA, int numberB) {
    Subtract request = new Subtract();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, SUBTRACT_ACTION, SubtractResponse.class);
  }

  public MultiplyResponse multiply(int numberA, int numberB) {
    Multiply request = new Multiply();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, MULTIPLY_ACTION, MultiplyResponse.class);
  }

  public DivideResponse divide(int numberA, int numberB) {
    Divide request = new Divide();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, DIVIDE_ACTION, DivideResponse.class);
  }

  private <T, E> T sendAction(E request, String action, Class<T> clazz) {
    return Optional.ofNullable(getWebServiceTemplate())
      .map(template -> template.marshalSendAndReceive(URI, request, new SoapActionCallback(action)))
      .map(clazz::cast)
      .orElseThrow(RuntimeException::new);
  }

}
