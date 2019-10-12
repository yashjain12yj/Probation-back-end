package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.logger.Lby4j;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.service.PostService;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static com.buynsell.buynsell.encryption.AuthenticationTokenUtil.getUsernameOrEmailFromToken;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    Lby4j log;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Value("${secretKey}")
    private String secretKey;

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(@Valid CreatePostDTO createPost, MultipartFile[] images, @RequestHeader("token") String token) {
        // extract username
        String usernameOrEmail = AuthenticationTokenUtil.getUsernameOrEmailFromToken(token, secretKey);

        // get user
        Optional<User> user = userService.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        CurrentUser.setCurrentUser(user.get());

        try {
            Item item = postService.createPost(createPost, images);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException while persisting Image"+e.toString());
        }
        // save the post
        return ResponseEntity.status(HttpStatus.OK).body("Post added, with post id:");
    }

    /*@PostMapping(value = "/image", consumes={"multipart/form-data"})
    public ResponseEntity<?> createPost(MultipartFile[] images) {
        Long id = 0L;
        try {
            id = postService.saveImage(images[0]);
        } catch (IOException e) {
            log.error("IOException in PostController.java - Line 36");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }*/
}
