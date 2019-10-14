package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SearchRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<Item> getRecentItems(){

        // get items from db which are not of current user.
        String mysqlQuery = "select * from item order by created_at desc limit 10";
        String oracleQuery = "select * from item order by created_at desc ROWNUM <= 10";

        String hql = "FROM Item item WHERE item.id != :itemId ORDER BY item.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("itemId", 10L);
        query.setMaxResults(10);
        List items = query.getResultList();


        return items;
    }
}
