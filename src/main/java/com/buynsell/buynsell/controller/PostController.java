package com.buynsell.buynsell.controller;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.EditItemDTO;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.payload.UserInfo;
import com.buynsell.buynsell.service.PostService;
import com.buynsell.buynsell.util.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
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
        Optional<String> validationErrorMessage = postValidator.validateCreatePost(createPostDTO);
        if (validationErrorMessage.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorMessage.get());
        Item item;
        try {
            item = postService.createPost(createPostDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(item.getId());
    }

    @GetMapping(value = "/post/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable Long itemId) {
        Optional<PostDTO> postDTO;
        try {
            postDTO = postService.getItem(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (postDTO.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(postDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Not found");
    }

    @PostMapping("/private/post/markSold")
    public ResponseEntity<?> markSold(@RequestBody Map<String, Long> keyVsValueMap) {
        boolean flag = false;
        try {
            flag = postService.markSold(keyVsValueMap.get("id"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (!flag)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        return ResponseEntity.status(HttpStatus.OK).body("Marked Sold!");
    }

    @PostMapping("/private/post/markAvailable")
    public ResponseEntity<?> markAvailable(@RequestBody Map<String, Long> keyVsValueMap) {
        boolean flag = false;
        try {
            flag = postService.markAvailable(keyVsValueMap.get("id"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (!flag)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        return ResponseEntity.status(HttpStatus.OK).body("Changed Availability");
    }

    @DeleteMapping("/private/post/delete")
    public ResponseEntity<?> deletePost(@RequestBody Map<String, Long> keyVsValueMap) {
        boolean flag = false;
        try {
            flag = postService.deletePost(keyVsValueMap.get("id"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        if (!flag)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }

    @PostMapping(value = "/private/post/edit", consumes = {"multipart/form-data"})
    public ResponseEntity<?> editPost(EditItemDTO editItemDTO) {
        Optional<String> validationErrorMessage = postValidator.validateEditPost(editItemDTO);
        if (validationErrorMessage.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorMessage.get());
        try {
            postService.editPost(editItemDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }
}
