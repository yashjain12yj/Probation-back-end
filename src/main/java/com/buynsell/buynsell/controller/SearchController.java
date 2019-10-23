package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.payload.SearchRequestDTO;
import com.buynsell.buynsell.service.SearchService;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.SearchValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private UserService userService;

    @Autowired
    private SearchValidator searchValidator;

    @Autowired
    private AuthenticationTokenUtil authenticationTokenUtil;

    @GetMapping("/recentitems")
    public ResponseEntity<?> getRecentItems() {
        List items = searchService.getRecentItems(null);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @PostMapping("/searchItems")
    public ResponseEntity<?> searchItem(SearchRequestDTO searchRequestDTO, @RequestHeader HttpHeaders headers){
        String searchQuery = headers.get("searchQuery").get(0);
        ResponseEntity responseEntity = searchValidator.validateSearchQuery(searchQuery);
        if (responseEntity != null)
            return responseEntity;
        List items = searchService.getSearchResult(null, searchQuery);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
