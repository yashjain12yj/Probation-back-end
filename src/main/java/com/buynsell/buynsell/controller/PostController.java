package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.logger.Lby4j;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.service.PostService;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    Lby4j log;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    PostValidator postValidator;

    @Value("${secretKey}")
    private String secretKey;

    @Value("${tokenSecretKey}")
    private String tokenSecretKey;

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(@Valid CreatePostDTO createPostDTO, @RequestHeader("token") String token) {

        // Extract username from token
        String usernameOrEmail = AuthenticationTokenUtil.getUsernameOrEmailFromToken(token, tokenSecretKey);
        // Get user
        Optional<User> user = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (!user.isPresent())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");

        // Validate input
        ResponseEntity responseEntity = postValidator.validateCreatePost(createPostDTO);
        if (responseEntity != null)
            return responseEntity;

        Item item = postService.createPost(createPostDTO, user.get());
        if (item == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some Error Occurred, Retry!");
        return ResponseEntity.status(HttpStatus.OK).body(item.getId());
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Long itemId) {
        PostDTO postDTO = postService.getItem(itemId);
        if (postDTO == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }
}
