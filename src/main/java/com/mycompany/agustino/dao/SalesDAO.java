package com.mycompany.agustino.dao;

import com.mycompany.agustino.HibernateUtil;
import com.mycompany.agustino.model.SalesTransaction;
import java.math.BigDecimal;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SalesDAO {

    public void save(SalesTransaction sale) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(sale);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<SalesTransaction> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM SalesTransaction ORDER BY tanggalJual DESC", SalesTransaction.class).list();
        }
    }

    public BigDecimal getTotalProfit() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            BigDecimal total = session.createQuery("SELECT COALESCE(SUM(s.keuntungan), 0) FROM SalesTransaction s", BigDecimal.class).uniqueResult();
            return total != null ? total : BigDecimal.ZERO;
        }
    }
}