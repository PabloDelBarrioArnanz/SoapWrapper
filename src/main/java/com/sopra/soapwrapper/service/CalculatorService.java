package com.sopra.soapwrapper.service;

import com.sopra.soapwrapper.comunication.SoapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  @Autowired private SoapClient soapClient;

  public int add(int numberA, int  numberB) {
    return soapClient.add(numberA, numberB).getAddResult();
  }

  public int subtract(int numberA, int  numberB) {
    return soapClient.subtract(numberA, numberB).getSubtractResult();
  }

  public int multiply(int numberA, int  numberB) {
    return soapClient.multiply(numberA, numberB).getMultiplyResult();
  }

  public int divide(int numberA, int  numberB) {
    return soapClient.divide(numberA, numberB).getDivideResult();
  }
}