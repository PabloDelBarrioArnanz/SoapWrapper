package com.sopra.soapwrapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class IntegrationTest {

  private MockMvc mvc;
  @Autowired private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void addIntegrationTestReturnOk() throws Exception {
    mvc.perform(get("http://localhost:8080/calculator/add?numberA=2&numberB=1"))
      .andExpect(status().isOk())
      .andExpect(content().string("3"));
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void subtractIntegrationTestReturnOk() throws Exception {
    mvc.perform(get("http://localhost:8080/calculator/subtract?numberA=2&numberB=1"))
      .andExpect(status().isOk())
      .andExpect(content().string("1"));
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void multiplyIntegrationTestReturnOk() throws Exception {
    mvc.perform(get("http://localhost:8080/calculator/multiply?numberA=2&numberB=1"))
      .andExpect(status().isOk())
      .andExpect(content().string("2"));
  }

  @Test
  @WithMockUser(username = "iamadmin@soprasteria.com", roles = {"ADMIN"})
  public void divideIntegrationTestReturnOk() throws Exception {
    mvc.perform(get("http://localhost:8080/calculator/divide?numberA=2&numberB=1"))
      .andExpect(status().isOk())
      .andExpect(content().string("2"));
  }
}