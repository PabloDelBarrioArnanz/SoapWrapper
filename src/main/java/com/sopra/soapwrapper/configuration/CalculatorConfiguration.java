package com.sopra.soapwrapper.configuration;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.spring.security.api.JwtAuthenticationProvider;
import com.sopra.soapwrapper.comunication.SoapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
public class CalculatorConfiguration {
  
  @Value(value = "${auth0.apiAudience}") private String apiAudience;
  @Value(value = "${auth0.issuer}") private String issuer;

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
  
  @Bean
  public AuthenticationProvider authenticationProvider() {
    return new JwtAuthenticationProvider(new UrlJwkProvider(issuer), issuer, apiAudience);
  }
}
