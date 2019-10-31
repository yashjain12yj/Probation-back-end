package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(User user) throws Exception {
        em.persist(user);
    }

    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :usernameOrEmail or u.email = :usernameOrEmail");
        query.setParameter("usernameOrEmail", usernameOrEmail.toLowerCase());
        List<User> resultList = query.getResultList();
        if (resultList.size() == 0)
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }

    public Boolean existsByUsername(String username) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username.toLowerCase());
        return query.getResultList().size() == 1;
    }

    public Boolean existsByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email.toLowerCase());
        return query.getResultList().size() == 1;
    }
}
