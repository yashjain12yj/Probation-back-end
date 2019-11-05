package com.buynsell.buynsell.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;



    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getRecentItems() throws Exception {
        List items = searchRepository.getRecentItems();
        assertEquals("Checking number of recent post written", 2,items.size());
    }

    @Test
    public void getSearchResult() throws Exception {
        List items = searchRepository.getSearchResult("car");
        assertEquals("Checking number of search result of a query", 2, items.size());
    }
}