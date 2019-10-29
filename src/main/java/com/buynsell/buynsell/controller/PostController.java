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
        PostDTO postDTO = postService.getItem(itemId);
        if (postDTO == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item Not found/ Error");
        return ResponseEntity.status(HttpStatus.OK).body(postDTO);
    }

    @PostMapping("/private/post/markSold")
    public ResponseEntity<?> markSold(@RequestBody Map<String, Long> keyVsValueMap) {
        boolean flag = postService.markSold(keyVsValueMap.get("id"));
        if (!flag)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        return ResponseEntity.status(HttpStatus.OK).body("Changed Availability");
    }

    @PostMapping("/private/post/markAvailable")
    public ResponseEntity<?> markAvailable(@RequestBody Map<String, Long> keyVsValueMap) {
        boolean flag = postService.markAvailable(keyVsValueMap.get("id"));
        if (!flag)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        return ResponseEntity.status(HttpStatus.OK).body("Changed Availability");
    }

    @PostMapping("/private/post/delete")
    public ResponseEntity<?> deletePost(@RequestBody Map<String, Long> keyVsValueMap) {
        boolean flag = postService.deletePost(keyVsValueMap.get("id"));
        if (!flag)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Post not found");
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
    }

    @PostMapping(value = "/private/post/edit", consumes = {"multipart/form-data"})
    public ResponseEntity<?> editPost( EditItemDTO editItemDTO){
        ResponseEntity re = postValidator.validateEditPost(editItemDTO);
        if (re != null)
            return re;

        boolean res = postService.editPost(editItemDTO);
        if (res){
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
    }
}
