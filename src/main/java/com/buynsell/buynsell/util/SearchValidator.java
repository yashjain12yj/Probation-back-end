package com.buynsell.buynsell.util;

import com.buynsell.buynsell.payload.SearchRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchValidator {
    public ResponseEntity<String> validateSearchQuery(SearchRequestDTO searchRequestDTO){
        if (searchRequestDTO == null || searchRequestDTO.getSearchQuery() == null || searchRequestDTO.getSearchQuery().trim().equals(""))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Search query is empty");
        return null;
    }
}
