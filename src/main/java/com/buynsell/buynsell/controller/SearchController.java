package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.SearchRequestDTO;
import com.buynsell.buynsell.service.SearchService;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.SearchValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    UserService userService;

    @Autowired
    SearchValidator searchValidator;

    @GetMapping("/recentitems")
    public ResponseEntity<?> getRecentItems(@RequestHeader HttpHeaders headers) {
        Optional<User> user = AuthenticationTokenUtil.getUserFromHeader(headers);

        List items = searchService.getRecentItems(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @PostMapping("/searchItems")
    public ResponseEntity<?> searchItem(SearchRequestDTO searchRequestDTO, @RequestHeader HttpHeaders headers){
        String searchQuery = headers.get("searchQuery").get(0);

        ResponseEntity responseEntity = searchValidator.validateSearchQuery(searchQuery);

        if (responseEntity != null)
            return responseEntity;

        Optional<User> user = AuthenticationTokenUtil.getUserFromHeader(headers);

        List items = searchService.getSearchResult(user.get(), searchQuery);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
}
