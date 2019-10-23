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
    public List<Item> getRecentItems() {
        try {
            String hql = "FROM Item item WHERE item.isAvailable = true ORDER BY item.createdAt DESC";
            Query query = entityManager.createQuery(hql);
            query.setMaxResults(numberOfRecentPost);
            List items = query.getResultList();
            return items;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Item> getSearchResult(String searchQuery) {
        try{
            String hql = "FROM Item item WHERE item.isAvailable = true AND item.searchString LIKE '%' || :searchQuery || '%' ORDER BY item.createdAt DESC";
            Query query = entityManager.createQuery(hql);
            query.setParameter("searchQuery", searchQuery.toLowerCase());
            List items = query.getResultList();
            return items;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
