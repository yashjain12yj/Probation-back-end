package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    public Item createPost(CreatePostDTO createPost, MultipartFile[] images) throws IOException {
        // Get the user object to add item
        Optional<User> optionalUser = userRepository.findById(CurrentUser.getCurrentUser().getId());
        if (!optionalUser.isPresent()) return null;
        User user = optionalUser.get();

        // create item and set props
        Item item = new Item();
        item.setTitle(createPost.getTitle());
        item.setDescription(createPost.getDescription());
        item.setPrice(Double.parseDouble(createPost.getPrice()));
        item.setAvailable(true);
        item.setContactName(createPost.getContactName());
        item.setContactEmail(createPost.getContactEmail());
        item.setCategory(createPost.getCategory());
        item.setUser(user);

        // add all the images to item
        for(int i = 0; i < images.length; i++){
            Image image = new Image();
            image.setData(images[i].getBytes());
            item.addImage(image);
        }

        //persist item
        entityManager.persist(item);

        // add item to user
        user.addItem(item);

        return item;
    }

}
