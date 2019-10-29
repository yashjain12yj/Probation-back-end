package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.SignUpRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
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
    public User save(User user) {
        if (!existsByEmail(user.getEmail())) {
            try{
                em.persist(user);
                return user;
            }catch (Exception ex){
                return null;
            }
        } else {
            try{
                return em.merge(user);
            }catch (Exception ex){
                return null;
            }
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<User> findByUsernameOrEmail(String usernameOrEmail) {
        System.out.println("Getting Data from database");
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username or u.email = :email");
        query.setParameter("username", usernameOrEmail.toLowerCase());
        query.setParameter("email",usernameOrEmail.toLowerCase());
        List<User> resultList = query.getResultList();
        if (resultList.size() == 0)
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }


    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Boolean existsByUsername(String username) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username.toLowerCase());
        return query.getResultList().size() == 1;
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Boolean existsByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email.toLowerCase());
        return query.getResultList().size() == 1;
    }
}
