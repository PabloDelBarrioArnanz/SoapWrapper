package com.sopra.soapwrapper.category;

public enum SOAPActionEndpoint {
  ADD("http://tempuri.org/Add"),
  SUBTRACT("http://tempuri.org/Subtract"),
  DIVIDE("http://tempuri.org/Multiply"),
  MULTIPLY("http://tempuri.org/Divide");
  private String value;

  SOAPActionEndpoint(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}