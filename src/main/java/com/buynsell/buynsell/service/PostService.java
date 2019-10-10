package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public Item createPost(CreatePostDTO createPost, MultipartFile[] images) throws IOException {
        return postRepository.createPost(createPost, images);
    }
}
