package com.mycompany.agustino.dao;

import com.mycompany.agustino.model.RedfingerSlot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RedfingerSlotDAO {

    @PersistenceContext
    private EntityManager em;

    public List<RedfingerSlot> findAll() {
        return em.createQuery("FROM RedfingerSlot", RedfingerSlot.class).getResultList();
    }

    public void save(RedfingerSlot slot) {
        em.persist(slot);
    }
}