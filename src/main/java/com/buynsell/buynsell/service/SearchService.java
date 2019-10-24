package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.payload.SearchRequestDTO;
import com.buynsell.buynsell.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private SearchRepository searchRepository;

    public List getRecentItems(){
        return searchRepository.getRecentItems();
    }

    public List<Item> getSearchResult(SearchRequestDTO searchRequestDTO){
        return searchRepository.getSearchResult(searchRequestDTO.getSearchQuery());
    }
}
