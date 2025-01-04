package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.entity.*;

@Repository
public class EventDao {
    @Autowired
    private SessionFactory sessionFactory;

    // Save or update an Event
    @Transactional
    public void saveOrUpdate(Event event) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(event);
    }

    // Find an Event by ID
    @Transactional
    public Event findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Event.class, id);
    }

    // Get all Events
    @Transactional
    public List<Event> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Event", Event.class).list();
    }

    // Delete an Event by ID
    @Transactional
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Event event = session.get(Event.class, id);
        if (event != null) {
            session.delete(event);
        }
    }
}