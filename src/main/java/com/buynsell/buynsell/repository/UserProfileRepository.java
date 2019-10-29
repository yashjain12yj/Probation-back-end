package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


@Repository
public class UserProfileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserService userService;

    /**
     * @param user
     * @return -1 if database error, 1 if successfully changed password, 0 if password doesn't match.
     */
    @Transactional
    public int changePassword(User user, String newPassword) {
        try {
            user.setPassword(newPassword);
            entityManager.merge(user);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }


    public List<Item> getDashboard(String username) {
        Optional<User> user = userService.findByUsernameOrEmail(username);
        String hql = "FROM Item item WHERE item.user.username = :username ORDER BY item.createdAt DESC";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", username);
        List items = query.getResultList();
        return items;
    }
}
