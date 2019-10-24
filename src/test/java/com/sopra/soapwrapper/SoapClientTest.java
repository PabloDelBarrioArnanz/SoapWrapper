package com.sopra.soapwrapper;

/*
import com.calculator.wsdl.Add;
import com.calculator.wsdl.AddResponse;
import com.sopra.soapwrapper.comunication.SoapClient;
import com.sopra.soapwrapper.configuration.CalculatorConfiguration;
import com.sopra.soapwrapper.controller.CalculatorController;
import com.sopra.soapwrapper.service.CalculatorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SoapClientTestConfiguration.class)
public class SoapClientTest {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    @Autowired
    private SoapClient soapClient;

    @Autowired
    private CalculatorController calculatorController;

    @Autowired
    private CalculatorConfiguration calculatorConfiguration;

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private WebServiceGatewaySupport webServiceGatewaySupport;

    @Test
    public void shouldReturn3() {
        Mockito.doReturn(new AddResponse()).when(webServiceTemplate).marshalSendAndReceive(anyString(), any(Add.class), any());
//        Mockito.when(webServiceTemplate.marshalSendAndReceive(any(), any(), any())).thenReturn(new AddResponse());
        ResponseEntity<Integer> response = calculatorController.add(1, 2);
        Mockito.verify(calculatorService, times(1)).add(1,2);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(Optional.of(response.getBody()), 3);
    }

}
 */
