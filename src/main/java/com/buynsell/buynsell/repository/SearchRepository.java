package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

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

    public List<Item> getRecentItems() throws Exception {
        String hql = "FROM Item item WHERE item.isAvailable = true ORDER BY item.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setMaxResults(numberOfRecentPost);
        List items = query.getResultList();
        return items;
    }

    public List<Item> getSearchResult(String searchQuery) throws Exception {
        String hql = "FROM Item item WHERE item.isAvailable = true AND item.searchString LIKE '%' || :searchQuery || '%' ORDER BY item.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("searchQuery", searchQuery.toLowerCase());
        List items = query.getResultList();
        return items;
    }
}
