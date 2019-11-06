package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.payload.LoginRequest;
import com.buynsell.buynsell.payload.SignUpRequest;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private static final Logger LOG = Logger.getLogger(AuthControllerIntegrationTest.class.getName());

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSignInForValidUser() {
        LOG.info("###########Running test case for testSignInForValidUser()###########");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("jainy");
        loginRequest.setPassword("Yash@123");
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(loginRequest), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/auth/signin"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSignInForInvalidUser() throws Exception {
        LOG.info("###########Running test case for testSignInForInvalidUser()###########");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail("jainy123");
        loginRequest.setPassword("Yash@123");
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(loginRequest), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/auth/signin"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testSignUpWithInvalidFields() throws Exception {
        LOG.info("###########Running test case for testSignUpWithInvalidFields()###########");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Yash Jain");
        signUpRequest.setUsername("jainy");
        signUpRequest.setEmail("jainy@arezzosky.com");
        signUpRequest.setPassword("Yash@123");
        signUpRequest.setConfirmPassword("Yash@123");
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(signUpRequest), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/auth/signup"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testSignUpWithValidFields() throws Exception {
        LOG.info("###########Running test case for testSignUpWithValidFields()###########");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName("Yash Jain");
        signUpRequest.setUsername("jainy1");
        signUpRequest.setEmail("jainy1@arezzosky.com");
        signUpRequest.setPassword("Yash@123");
        signUpRequest.setConfirmPassword("Yash@123");
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(signUpRequest), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/auth/signup"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
