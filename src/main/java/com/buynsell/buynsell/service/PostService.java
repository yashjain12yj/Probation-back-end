package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.EditItemDTO;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.payload.UserInfo;
import com.buynsell.buynsell.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserInfo userInfo;

    @Autowired
    UserService userService;

    @Transactional
    public Item createPost(CreatePostDTO createPostDTO) {
        Item item = new Item();
        item.setTitle(createPostDTO.getTitle());
        item.setDescription(createPostDTO.getDescription());
        item.setPrice(Double.parseDouble(createPostDTO.getPrice()));
        item.setAvailable(true);
        item.setCategory(createPostDTO.getCategory());
        Optional<User> user = userService.findByUsernameOrEmail(userInfo.getEmail());
        if (!user.isPresent()) return null;
        item.setUser(user.get());
        user.get().addItem(item);
        for (int i = 0; i < createPostDTO.getImages().length; i++) {
            Image image = new Image();
            try {
                image.setData(createPostDTO.getImages()[i].getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            image.setItem(item);
            item.addImage(image);
        }
        return postRepository.createPost(item);
    }

    public PostDTO getItem(Long itemId) {
        Item item = postRepository.getItem(itemId);
        if (item == null) return null;
        PostDTO postDTO = new PostDTO();
        postDTO.setId(item.getId());
        postDTO.setTitle(item.getTitle());
        postDTO.setDescription(item.getDescription());
        postDTO.setCategory(item.getCategory());
        postDTO.setPrice(item.getPrice());
        postDTO.setAvailable(item.isAvailable());
        postDTO.setCreatedAt(Timestamp.from(item.getCreatedAt()).toString());
        for (Image image : item.getImages())
            postDTO.addImage(image);
        User user = new User();
        user.setName(item.getUser().getName());
        user.setUsername(item.getUser().getUsername());
        user.setEmail(item.getUser().getEmail());
        postDTO.setUser(user);
        return postDTO;
    }

    public boolean markSold(long itemId) {
        return postRepository.markSold(userInfo.getUsername(), itemId);
    }

    public boolean markAvailable(long itemId) {
        return postRepository.markAvailable(userInfo.getUsername(), itemId);
    }
    public boolean deletePost(long itemId) {
        return postRepository.deletePost(userInfo.getUsername(), itemId);
    }

    public boolean editPost(EditItemDTO editItemDTO){
        Long id = editItemDTO.getId();
        Item item = postRepository.getItem(id);
        if(!item.getUser().getUsername().equals(userInfo.getUsername())) return false;

        item.setTitle(editItemDTO.getTitle());
        item.setDescription(editItemDTO.getDescription());
        item.setCategory(editItemDTO.getCategory());
        item.setPrice(Double.parseDouble(editItemDTO.getPrice()));
        if (editItemDTO.getImages()!=null){
            for (int i = 0; i < editItemDTO.getImages().length; i++) {
                Image image = new Image();
                try {
                    image.setData(editItemDTO.getImages()[i].getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
                image.setItem(item);
                item.addImage(image);
            }
        }
        return postRepository.editPost(userInfo.getUsername(), item);
    }

}
