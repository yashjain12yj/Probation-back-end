package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.logger.Lby4j;
import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.PostDTO;
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
import java.util.Date;
import java.util.Optional;
import java.util.Set;

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
            Item item = postService.createPost(createPost);
            return ResponseEntity.status(HttpStatus.OK).body(item.getId());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException while persisting Image" + e.toString());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some Error Occurred, Retry!");
    }

    @GetMapping(value = "/{itemId}")
    public ResponseEntity<PostDTO> getItem(@PathVariable Long itemId) {
        log.info("The post id is " + itemId);
        return ResponseEntity.status(HttpStatus.OK).body(postService.getItem(itemId));
    }
}
