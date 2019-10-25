package com.sopra.soapwrapper.category;

public enum SOAPActionEndpoint {
  ADD("http://tempuri.org/Add"),
  SUBTRACT("http://tempuri.org/Subtract"),
  DIVIDE("http://tempuri.org/Divide"),
  MULTIPLY("http://tempuri.org/Multiply");
  private String value;

  SOAPActionEndpoint(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}