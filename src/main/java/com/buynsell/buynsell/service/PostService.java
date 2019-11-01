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
    public Item createPost(CreatePostDTO createPostDTO) throws Exception {
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
            image.setData(createPostDTO.getImages()[i].getBytes());
            item.addImage(image);
        }
        return postRepository.createPost(item);
    }

    public Optional<PostDTO> getItem(Long itemId) throws Exception {
        Optional<Item> item = postRepository.getItem(itemId);
        if (!item.isPresent()) return Optional.empty();
        PostDTO postDTO = new PostDTO();
        postDTO.setId(item.get().getId());
        postDTO.setTitle(item.get().getTitle());
        postDTO.setDescription(item.get().getDescription());
        postDTO.setCategory(item.get().getCategory());
        postDTO.setPrice(item.get().getPrice());
        postDTO.setAvailable(item.get().isAvailable());
        postDTO.setCreatedAt(Timestamp.from(item.get().getCreatedAt()).toString());
        for (Image image : item.get().getImages())
            postDTO.addImage(image);
        User user = new User();
        user.setName(item.get().getUser().getName());
        user.setUsername(item.get().getUser().getUsername());
        user.setEmail(item.get().getUser().getEmail());
        postDTO.setUser(user);
        return Optional.of(postDTO);
    }

    public boolean markSold(long itemId) throws Exception {
        return postRepository.markSold(userInfo.getUsername(), itemId);
    }

    public boolean markAvailable(long itemId) throws Exception {
        return postRepository.markAvailable(userInfo.getUsername(), itemId);
    }

    public boolean deletePost(long itemId) throws Exception {
        return postRepository.deletePost(userInfo.getUsername(), itemId);
    }

    public boolean editPost(EditItemDTO editItemDTO) throws Exception {
        Long id = editItemDTO.getId();
        Optional<Item> optItem = postRepository.getItem(id);

        if (!optItem.isPresent() || !optItem.get().getUser().getUsername().equals(userInfo.getUsername())) return false;
        Item item = optItem.get();
        item.setTitle(editItemDTO.getTitle());
        item.setDescription(editItemDTO.getDescription());
        item.setCategory(editItemDTO.getCategory());
        item.setPrice(Double.parseDouble(editItemDTO.getPrice()));
        if (editItemDTO.getImages() != null) {
            for (int i = 0; i < editItemDTO.getImages().length; i++) {
                Image image = new Image();
                image.setData(editItemDTO.getImages()[i].getBytes());
                item.addImage(image);
            }
        }
        return postRepository.editPost(userInfo.getUsername(), item);
    }

}
