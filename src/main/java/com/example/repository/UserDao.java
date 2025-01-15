package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.entity.*;

@Repository
public class UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Transactional
    public User findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, email);
    }
    
    @SuppressWarnings("unchecked")
    public List<User> findAll() { // 1 - get all
        try (Session session = sessionFactory.openSession()) {
        	return session.createQuery("from User").getResultList();
        }
    }
    
    public void delete(String email) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            
            session.createNativeQuery("DELETE FROM authorities WHERE email = :email")
            .setParameter("email", email)
            .executeUpdate();
            
            session.createNativeQuery("DELETE FROM users WHERE email = :email")
                .setParameter("email", email)
                .executeUpdate();
            
            session.getTransaction().commit();
        }
    }
}