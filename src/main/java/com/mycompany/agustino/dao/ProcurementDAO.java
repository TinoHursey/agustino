package com.mycompany.agustino.dao;

import com.mycompany.agustino.model.ProcurementHPP;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProcurementDAO {

    @PersistenceContext
    private EntityManager em;

    public List<ProcurementHPP> findAll() {
        return em.createQuery("FROM ProcurementHPP p ORDER BY p.id DESC", ProcurementHPP.class)
                .getResultList();
    }

    public void save(ProcurementHPP data) {
        em.persist(data);
    }

    public BigDecimal getTotalModal() {
        BigDecimal total = em.createQuery(
                "SELECT SUM(p.hargaBeliTotal) FROM ProcurementHPP p", BigDecimal.class)
                .getSingleResult();
        return total != null ? total : BigDecimal.ZERO;
    }
}