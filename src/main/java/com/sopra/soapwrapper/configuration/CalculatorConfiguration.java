package com.sopra.soapwrapper.configuration;

import com.sopra.soapwrapper.comunication.SoapClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class CalculatorConfiguration {
  
  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("com.calculator.wsdl");
    return marshaller;
  }

  @Bean
  public SoapClient addClient(Jaxb2Marshaller marshaller) {
    SoapClient client = new SoapClient();
    client.setDefaultUri("http://www.dneonline.com/calculator.asmx");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}
