package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.model.Role;
import com.buynsell.buynsell.model.RoleName;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class RoleRepository {

    @PersistenceContext
    EntityManager em;

    Optional<Role> findByName(RoleName roleName) {
        Query query = em.createQuery("select role from Role role where role.name = :roleName");
        query.setParameter("roleName", roleName);
        Role role = (Role) query.getSingleResult();
        if(role == null) return Optional.empty();
        return Optional.of(role);
    }
}
