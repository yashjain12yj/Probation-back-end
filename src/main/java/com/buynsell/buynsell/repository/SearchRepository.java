package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.util.CurrentUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Value("${numberOfRecentPost}")
    private int numberOfRecentPost;

    public List<Item> getRecentItems(User user) {
        long userId;
        if (user == null) {
            userId = 0l;
        } else {
            userId = user.getId();
        }
        // get items from db which are not of current user.
        String hql = "FROM Item item WHERE item.user.id != :userId ORDER BY item.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("userId", userId);
        query.setMaxResults(numberOfRecentPost);
        List items = query.getResultList();

        return items;
    }
}