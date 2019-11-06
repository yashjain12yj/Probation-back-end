package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.payload.SearchRequestDTO;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import org.junit.Assert;
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
public class SearchControllerIntegrationTest {
    @LocalServerPort
    private int port;

    private static final Logger LOG = Logger.getLogger(AuthControllerIntegrationTest.class.getName());

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetRecentItems(){
        LOG.info("###########Running test case for testGetRecentItems()###########");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/search/recentitems"), HttpMethod.GET, entity, String.class);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSearchItemsWithValidData(){
        LOG.info("###########Running test case for testSearchItemsWithValidData()###########");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        SearchRequestDTO searchRequestDTO = new SearchRequestDTO();
        searchRequestDTO.setSearchQuery("test");
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        HttpEntity<String> entity = new HttpEntity<String>(jacksonJsonProvider.toJson(searchRequestDTO), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/search/searchItems"), HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
