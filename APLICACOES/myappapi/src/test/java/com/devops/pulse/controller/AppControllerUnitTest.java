package com.devops.pulse.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devops.pulse.controllers.AppController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class AppControllerUnitTest {

    @InjectMocks
    private AppController controller;


  /*
    @Before
    public void setup(){
        this.controller = new AppController();
    }
    */
    @Test
    public void deveRetornarSucessoAoRetornarOgreeting(){
        ResponseEntity<Map<String, String>> response = controller.api();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Aplica ndo test ... ", response.getBody().get("greeting"));
    }

    @Test
    public void deveRetornarSucesso(){
        ResponseEntity<String> response = controller.index();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Aplicando test ... ", response.getBody());
    }

}
