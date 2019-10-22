package com.sopra.soapwrapper.service;

import com.calculator.wsdl.AddResponse;
import com.calculator.wsdl.DivideResponse;
import com.calculator.wsdl.MultiplyResponse;
import com.calculator.wsdl.SubtractResponse;
import com.sopra.soapwrapper.comunication.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalculatorService {

  @Autowired private SoapClient soapClient;

  public int add(int numberA, int  numberB) {
    return Optional.of(soapClient.add(numberA, numberB))
      .map(AddResponse::getAddResult)
      .orElseThrow(RuntimeException::new);
  }

  public int subtract(int numberA, int  numberB) {
    return Optional.of(soapClient.subtract(numberA, numberB))
      .map(SubtractResponse::getSubtractResult)
      .orElseThrow(RuntimeException::new);
  }

  public int multiply(int numberA, int  numberB) {
    return Optional.of(soapClient.multiply(numberA, numberB))
      .map(MultiplyResponse::getMultiplyResult)
      .orElseThrow(RuntimeException::new);
  }

  public int divide(int numberA, int  numberB) {
    return Optional.of(soapClient.divide(numberA, numberB))
      .map(DivideResponse::getDivideResult)
      .orElseThrow(RuntimeException::new);
  }


}
