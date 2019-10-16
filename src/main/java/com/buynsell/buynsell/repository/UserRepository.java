package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.encryption.AESEncryption;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    @PersistenceContext
    EntityManager em;


    @Transactional
    public User save(SignUpRequest signUpRequest) {
        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setActive(true);

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

    @Transactional
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username or u.email = :email");
        query.setParameter("username", username.toLowerCase());
        query.setParameter("email",email.toLowerCase());
        List<User> resultList = query.getResultList();
        if (resultList.size() == 0)
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }

    @Transactional
    public List<User> findByIdIn(List<Long> userIds) {
        List<User> users = new ArrayList<>();
        for (Long userId : userIds) {
            User user = em.find(User.class, userId);
            if (user != null) users.add(user);
        }
        return users;
    }

    @Transactional
    public Optional<User> findById(Long userId) {
        User user = em.find(User.class, userId);
        if (user == null)
            return Optional.empty();
        return Optional.of(user);
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email.toLowerCase());
        List<User> resultList = query.getResultList();
        if (resultList.size() == 0)
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }

    @Transactional
    public Optional<User> findByUsername(String username) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username.toLowerCase());
        List<User> resultList = query.getResultList();
        if (resultList.size() == 0)
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }

    @Transactional
    public Boolean existsByUsername(String username) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username");
        query.setParameter("username", username.toLowerCase());
        return query.getResultList().size() == 1;
    }

    @Transactional
    public Boolean existsByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email.toLowerCase());
        return query.getResultList().size() == 1;
    }
}
