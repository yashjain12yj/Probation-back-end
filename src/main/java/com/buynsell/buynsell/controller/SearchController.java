package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.SearchRequestDTO;
import com.buynsell.buynsell.service.SearchService;
import com.buynsell.buynsell.service.UserService;
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

    @Value("${secretKey}")
    private String secretKey;

    @Value("${tokenSecretKey}")
    private String tokenSecretKey;

    @Autowired
    SearchService searchService;

    @Autowired
    UserService userService;

    @GetMapping("/recentitems")
    public ResponseEntity<?> getRecentItems(@RequestHeader HttpHeaders headers) {
        User user = null;

        if (headers.get("token") != null) {
            String token = headers.get("token").get(0);

            String usernameOrEmail = AuthenticationTokenUtil.getUsernameOrEmailFromToken(token, tokenSecretKey);

            // get user
            Optional<User> optUser = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            if (optUser.isPresent()) {
                user = optUser.get();
            }
        }
        List items = searchService.getRecentItems(user);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @PostMapping("/searchItems")
    public ResponseEntity<?> searchItem(SearchRequestDTO searchRequestDTO, @RequestHeader HttpHeaders headers){

        String searchQuery = headers.get("searchQuery").get(0);
        List items = new ArrayList();
        if (searchQuery == null || searchQuery.trim().equals("")){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(items);
        }
        User user = null;

        if (headers.get("token") != null) {
            String token = headers.get("token").get(0);

            String usernameOrEmail = AuthenticationTokenUtil.getUsernameOrEmailFromToken(token, tokenSecretKey);

            // get user
            Optional<User> optUser = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            if (optUser.isPresent()) {
                user = optUser.get();
            }
        }

        items = searchService.getSearchResult(user, searchQuery);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

}
