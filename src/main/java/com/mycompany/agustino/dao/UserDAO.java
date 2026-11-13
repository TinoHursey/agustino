package com.mycompany.agustino.dao;

import com.mycompany.agustino.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public User findByUsernameAndPassword(String username, String password) {
        List<User> hasil = em.createQuery(
                "FROM User u WHERE u.username = :u AND u.password = :p", User.class)
                .setParameter("u", username)
                .setParameter("p", password)
                .getResultList();
        return hasil.isEmpty() ? null : hasil.get(0);
    }

    public Long countUsers() {
        return em.createQuery("SELECT COUNT(u) FROM User u", Long.class).getSingleResult();
    }

    public void save(User user) {
        em.persist(user);
    }
}