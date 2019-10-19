package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    SearchRepository searchRepository;
    public List getRecentItems(User user){
        return searchRepository.getRecentItems(user);
    }

    public List<Item> getSearchResult(User user, String searchQuery){
        return searchRepository.getSearchResult(user, searchQuery);
    }
}
