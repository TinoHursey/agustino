package com.mycompany.agustino;

import com.mycompany.agustino.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Agustino {
    
    public static void main(String[] args) {
        // Otomatis membuat akun demo jika tabel user masih kosong
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Long total = session.createQuery("SELECT COUNT(u) FROM User u", Long.class).uniqueResult();
            if (total == 0) {
                session.persist(new User("owner", "admin123", User.Role.OWNER));
                session.persist(new User("worker", "worker123", User.Role.WORKER));
                tx.commit();
                System.out.println("Akun demo (owner & worker) berhasil dibuat.");
            }
        } catch (Exception e) {
            System.err.println("Gagal inisialisasi akun awal: " + e.getMessage());
        }

        // Tampilkan Form Login
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}