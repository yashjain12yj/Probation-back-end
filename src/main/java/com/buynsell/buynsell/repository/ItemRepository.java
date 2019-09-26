package com.buynsell.buynsell.repository;




import com.buynsell.buynsell.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ItemRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Item insert(Item item){
        entityManager.persist(item);
        return item;
    }

    public List<Item> findAll(){
        // find all items and return in a list
        Item item = entityManager.find(Item.class,(long)1);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        return items;
    }
}
