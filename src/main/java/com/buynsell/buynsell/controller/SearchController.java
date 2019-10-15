package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.logger.Lby4j;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.service.SearchService;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Value("${secretKey}")
    private String secretKey;

    @Autowired
    SearchService searchService;

    @Autowired
    UserService userService;

    @GetMapping("/recentitems")
    public ResponseEntity<?> getRecentItems(@RequestHeader HttpHeaders headers) {
        User user = null;

        if (headers.get("token") != null) {
            String token = headers.get("token").get(0);

            String usernameOrEmail = AuthenticationTokenUtil.getUsernameOrEmailFromToken(token, secretKey);

            // get user
            Optional<User> optUser = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
            if (optUser.isPresent()) {
                user = optUser.get();
            }
        }
        List items = searchService.getRecentItems(user);
        System.out.println(items.toString());
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

}
