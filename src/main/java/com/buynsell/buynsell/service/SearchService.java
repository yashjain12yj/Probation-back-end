package com.buynsell.buynsell.service;

import com.buynsell.buynsell.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    SearchRepository searchRepository;
    public List getRecentItems(){
        return searchRepository.getRecentItems();
    }
}
