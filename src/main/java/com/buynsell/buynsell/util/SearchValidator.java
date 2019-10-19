package com.buynsell.buynsell.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchValidator {
    public ResponseEntity<String> validateSearchQuery(String searchQuery){
        if (searchQuery == null || searchQuery.trim().equals(""))
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Search query is empty");
        return null;
    }
}
