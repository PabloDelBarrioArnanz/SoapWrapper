package com.sopra.soapwrapper.comunication;

import com.calculator.wsdl.*;
import com.sopra.soapwrapper.category.SOAPActionEndpoint;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.Optional;

public class SoapClient extends WebServiceGatewaySupport {
  private static final String URI = "http://www.dneonline.com/calculator.asmx";

  public AddResponse add(int numberA, int numberB) {
    Add request = new Add();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, SOAPActionEndpoint.ADD.getValue(), AddResponse.class);
  }

  public SubtractResponse subtract(int numberA, int numberB) {
    Subtract request = new Subtract();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, SOAPActionEndpoint.SUBTRACT.getValue(), SubtractResponse.class);
  }

  public MultiplyResponse multiply(int numberA, int numberB) {
    Multiply request = new Multiply();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, SOAPActionEndpoint.MULTIPLY.getValue(), MultiplyResponse.class);
  }

  public DivideResponse divide(int numberA, int numberB) {
    Divide request = new Divide();
    request.setIntA(numberA);
    request.setIntB(numberB);
    return sendAction(request, SOAPActionEndpoint.DIVIDE.getValue(), DivideResponse.class);
  }

  private <T, E> T sendAction(E request, String endPoint, Class<T> clazz) {
    return Optional.ofNullable(getWebServiceTemplate())
            .map(template -> template.marshalSendAndReceive(URI, request, new SoapActionCallback(endPoint)))
            .map(clazz::cast)
            .orElseThrow(RuntimeException::new);
  }
}