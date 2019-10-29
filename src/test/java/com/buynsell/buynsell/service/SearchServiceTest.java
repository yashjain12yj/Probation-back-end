package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.payload.SearchRequestDTO;
import com.buynsell.buynsell.repository.SearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private SearchRepository searchRepository;

    private List<Item> expectedItems;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        prepareTestData();
    }

    @Test
    public void getRecentItems() {
        Mockito.when(searchRepository.getRecentItems()).thenReturn(expectedItems);
        List actualItems = searchService.getRecentItems();
        assertEquals("Check if actual and expected recent item are same", expectedItems.size(), actualItems.size());
    }

    @Test
    public void getSearchResult() {
        String searchQuery = "car";
        Mockito.when(searchRepository.getSearchResult(searchQuery)).thenReturn(expectedItems);
        SearchRequestDTO searchRequestDTO = new SearchRequestDTO(searchQuery);
        List actualResult = searchService.getSearchResult(searchRequestDTO);
        assertEquals("Check if number of searched result is equal to expected number of result.", expectedItems.size(), actualResult.size());
    }

    public void prepareTestData(){
        expectedItems = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            expectedItems.add(new Item());
        }
    }
}