package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Optional;

@Repository
public class PostRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Item createPost(CreatePostDTO createPostDTO, User user) {

        // create item and set props
        Item item = new Item();
        item.setTitle(createPostDTO.getTitle());
        item.setDescription(createPostDTO.getDescription());
        item.setPrice(Double.parseDouble(createPostDTO.getPrice()));
        item.setAvailable(true);
        item.setContactName(createPostDTO.getContactName());
        item.setContactEmail(createPostDTO.getContactEmail());
        item.setCategory(createPostDTO.getCategory());
        item.setUser(user);

        user.addItem(item);

        // add all the images to item
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

        //persist item
        try{
            entityManager.persist(item);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return item;
    }

    public PostDTO getItem(Long itemId) {
        Item item;
        try {
            item = entityManager.find(Item.class, itemId);
        } catch (Exception ex) {
            return null;
        }
        if (item == null)
            return null;
        PostDTO postDTO = new PostDTO();
        postDTO.setId(item.getId());
        postDTO.setTitle(item.getTitle());
        postDTO.setDescription(item.getDescription());
        postDTO.setCategory(item.getCategory());
        postDTO.setPrice(item.getPrice());
        postDTO.setAvailable(item.isAvailable());
        postDTO.setContactName(item.getContactName());
        postDTO.setContactEmail(item.getContactEmail());
        postDTO.setCreatedAt(item.getCreatedAt());

        // to add images to ImageDTO
        for (Image image : item.getImages()) {
            postDTO.addImage(image);
        }

        User user = new User();
        user.setName(item.getUser().getName());
        user.setUsername(item.getUser().getUsername());
        user.setEmail(item.getUser().getEmail());
        postDTO.setUser(user);
        return postDTO;
    }

}
