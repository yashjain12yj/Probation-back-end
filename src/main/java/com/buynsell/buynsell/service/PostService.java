package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Item createPost(CreatePostDTO createPostDTO, User user){
        return postRepository.createPost(createPostDTO, user);
    }

    public PostDTO getItem(Long itemId) {
        return postRepository.getItem(itemId);
    }
}
