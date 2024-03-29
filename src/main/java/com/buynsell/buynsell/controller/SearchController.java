package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.payload.SearchRequestDTO;
import com.buynsell.buynsell.service.SearchService;
import com.buynsell.buynsell.util.SearchValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private SearchValidator searchValidator;

    @GetMapping("/recentitems")
    public ResponseEntity<?> getRecentItems() {
        List items = null;
        try {
            items = searchService.getRecentItems();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (items == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @PostMapping("/searchItems")
    public ResponseEntity<?> searchItems(@RequestBody SearchRequestDTO searchRequestDTO) {
        Optional<String> validationErrorMessage = searchValidator.validateSearchQuery(searchRequestDTO);
        if (validationErrorMessage.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorMessage.get());;
        List items = new ArrayList();
        try {
            items = searchService.getSearchResult(searchRequestDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (items.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content found");
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
