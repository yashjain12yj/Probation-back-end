package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.encryption.AuthKeys;
import com.buynsell.buynsell.encryption.AuthenticationTokenUtil;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.payload.UserInfo;
import com.buynsell.buynsell.service.PostService;
import com.buynsell.buynsell.service.UserService;
import com.buynsell.buynsell.util.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class PostController {

    private static final Logger log = Logger.getLogger(PostController.class.getName());

    @Autowired
    private PostService postService;

    @Autowired
    private PostValidator postValidator;

    @Autowired
    UserInfo userInfo;

    @PostMapping(value = "/private/post/", consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(@Valid CreatePostDTO createPostDTO) {
        log.info("/private/post/ -> started");
        ResponseEntity responseEntity = postValidator.validateCreatePost(createPostDTO);
        if (responseEntity != null)
            return responseEntity;
        Item item = postService.createPost(createPostDTO);
        if (item == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Some Error Occurred, Retry!");
        return ResponseEntity.status(HttpStatus.OK).body(item.getId());
    }

    @GetMapping(value = "/post/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Long itemId) {
        log.info("/post/{itemId} -> started");
        PostDTO postDTO = postService.getItem(itemId);
        if (postDTO == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Not found/ Error");
        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }

    @PostMapping("/private/post/markSold")
    public ResponseEntity<?> markSold(@RequestHeader("itemId") String itemId) {
        log.info("/private/post/markSold -> Started");
        long id;
        try {
            id = Long.parseLong(itemId);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid item id");
        }
        boolean flag = postService.markSold(id);
        if (!flag)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        return ResponseEntity.status(HttpStatus.OK).body("Changed Availability");
    }

    @PostMapping("/private/post/markAvailable")
    public ResponseEntity<?> markAvailable(@RequestHeader("itemId") String itemId) {
        log.info("/private/post/markAvailable -> Started");
        long id;
        try {
            id = Long.parseLong(itemId);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong item id");
        }
        boolean flag = postService.markAvailable(id);
        if (!flag)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        return ResponseEntity.status(HttpStatus.OK).body("Changed Availability");
    }
}
