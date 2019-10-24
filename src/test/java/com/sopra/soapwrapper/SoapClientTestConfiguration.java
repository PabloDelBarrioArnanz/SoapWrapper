package com.sopra.soapwrapper;

import com.sopra.soapwrapper.comunication.SoapClient;
import com.sopra.soapwrapper.configuration.CalculatorConfiguration;
import com.sopra.soapwrapper.controller.CalculatorController;
import com.sopra.soapwrapper.service.CalculatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@Configuration
public class SoapClientTestConfiguration {

  @Bean
  public SoapClient soapClient() {
    return new SoapClient();
  }

  @Bean
  public WebServiceTemplate webServiceTemplate() {
    return mock(WebServiceTemplate.class);
  }

  @Bean
  public WebServiceGatewaySupport webServiceGatewaySupport(){
    return spy(WebServiceGatewaySupport.class);
  }

  @Bean
  public CalculatorController calculatorController() {
    return new CalculatorController();
  }

  @Bean
  public CalculatorConfiguration calculatorConfiguration() {
    return mock(CalculatorConfiguration.class);
  }

  @Bean
  public CalculatorService calculatorService() {
    return new CalculatorService();
  }
}
