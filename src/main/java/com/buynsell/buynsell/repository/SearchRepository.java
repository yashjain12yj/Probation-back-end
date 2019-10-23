package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${numberOfRecentPost}")
    private int numberOfRecentPost;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Item> getRecentItems(User user) {
        long userId;
        if (user == null)
            userId = 0l;
        else
            userId = user.getId();
        String hql = "FROM Item item WHERE item.user.id != :userId AND item.isAvailable = true ORDER BY item.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("userId", userId);
        query.setMaxResults(numberOfRecentPost);
        List items = query.getResultList();
        return items;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Item> getSearchResult(User user, String searchQuery){
        long userId;
        if (user == null)
            userId = 0l;
        else
            userId = user.getId();

        String hql = "FROM Item item WHERE item.user.id != :userId AND item.isAvailable = true AND item.searchString LIKE '%' || :searchQuery || '%' ORDER BY item.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("searchQuery", searchQuery.toLowerCase());
        List items = query.getResultList();

        return items;
    }
}
