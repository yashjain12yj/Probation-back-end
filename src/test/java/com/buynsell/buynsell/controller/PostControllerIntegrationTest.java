package com.buynsell.buynsell.controller;

import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostControllerIntegrationTest {
    @LocalServerPort
    private int port;

    private static final Logger LOG = Logger.getLogger(AuthControllerIntegrationTest.class.getName());

    @Autowired
    private TestRestTemplate restTemplate;

    private Long itemId = 0L;

    @Test
    public void testGetItemWithValidData() {
        LOG.info("###########Running test case for testGetItemWithValidData()###########");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        String itemId = "1";
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/post/" + itemId), HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testBCreatePostWithValidData() throws IOException {
        LOG.info("###########Running test case for testACreatePostWithValidData()###########");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("token", "24si6t9HO3pXWLbTA8lArQ==");
        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<String, Object>();
        parameters.add("title", "Test title, Test title, Test title");
        parameters.add("description", "Test description, Test description, Test description, Test description");
        parameters.add("category", "test");
        parameters.add("price", "2000");
        parameters.add("images[0]", new ClassPathResource("testImages/hero-karizma-front-three-quarter-7427.png"));
        parameters.add("images[1]", new ClassPathResource("testImages/hero-karizma-front-three-quarter-7428.png"));
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<LinkedMultiValueMap<String, Object>>(parameters, headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/private/post/"), HttpMethod.POST, entity, String.class, "");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        if (response.getStatusCode().equals(HttpStatus.OK))
            itemId = Long.parseLong(response.getBody());
    }

    @Test
    public void testMarkSoldWithValidData(){
        LOG.info("###########Running test case for testMarkSoldWithValidData()###########");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", "24si6t9HO3pXWLbTA8lArQ==");

        Map<String, Long> payload = new HashMap<>();
        payload.put("id", 1L);
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(payload), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/private/post/markSold"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testMarkAvailableWithValidData(){
        LOG.info("###########Running test case for testMarkAvailableWithValidData()###########");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", "24si6t9HO3pXWLbTA8lArQ==");

        Map<String, Long> payload = new HashMap<>();
        payload.put("id", 1L);
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(payload), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/private/post/markAvailable"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testADeletePostWithValidData(){
        LOG.info("###########Running test case for testBDeletePostWithValidData()###########");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", "24si6t9HO3pXWLbTA8lArQ==");

        Map<String, String> payload = new HashMap<>();
        payload.put("id", String.valueOf(itemId));
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(payload), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/private/post/delete"), HttpMethod.DELETE, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
