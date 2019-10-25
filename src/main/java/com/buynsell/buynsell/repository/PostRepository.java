package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Item createPost(Item item) {
        try{
            entityManager.persist(item);
            return item;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Item getItem(Long itemId) {
        Item item;
        try {
            item = entityManager.find(Item.class, itemId);
        } catch (Exception ex) {
            return null;
        }
        if (item == null)
            return null;
        return item;
    }

    @Transactional
    public boolean markSold(String username, long itemId){
        Item item = entityManager.find(Item.class, itemId);
        if (item == null)
            return false;
        if (!item.getUser().getUsername().equals(username))
            return false;
        item.setAvailable(false);
        return true;
    }

    @Transactional
    public boolean markAvailable(String username, long itemId){
        Item item = entityManager.find(Item.class, itemId);
        if (item == null)
            return false;
        if (!item.getUser().getUsername().equals(username))
            return false;
        item.setAvailable(true);
        return true;
    }

    @Transactional
    public boolean deletePost(String username, long itemId){
        Item item = entityManager.find(Item.class, itemId);
        if (item == null)
            return false;
        if (!item.getUser().getUsername().equals(username))
            return false;
        try{
            entityManager.remove(item);
        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
