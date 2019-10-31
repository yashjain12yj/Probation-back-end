package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class PostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Item createPost(Item item) throws Exception {
        entityManager.persist(item);
        return item;
    }

    public Optional<Item> getItem(Long itemId) throws Exception {
        Item item;
        item = entityManager.find(Item.class, itemId);
        if (item == null)
            return Optional.empty();
        return Optional.of(item);
    }

    @Transactional
    public boolean markSold(String username, long itemId) throws Exception{
        Item item = entityManager.find(Item.class, itemId);
        if (item == null)
            return false;
        if (!item.getUser().getUsername().equals(username))
            return false;
        item.setAvailable(false);
        return true;
    }

    @Transactional
    public boolean markAvailable(String username, long itemId) throws Exception{
        Item item = entityManager.find(Item.class, itemId);
        if (item == null)
            return false;
        if (!item.getUser().getUsername().equals(username))
            return false;
        item.setAvailable(true);
        return true;
    }

    @Transactional
    public boolean deletePost(String username, long itemId) throws Exception{
        Item item = entityManager.find(Item.class, itemId);
        if (item == null)
            return false;
        if (!item.getUser().getUsername().equals(username))
            return false;
        entityManager.remove(item);
        return true;
    }

    @Transactional
    public boolean editPost(String username, Item item) throws Exception{
        if (item == null)
            return false;
        if (!item.getUser().getUsername().equals(username))
            return false;
        entityManager.merge(item);
        return true;
    }
}
