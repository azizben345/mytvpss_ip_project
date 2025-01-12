package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.entity.*;

@Repository
public class RecruitmentDao {
    @Autowired
    private SessionFactory sessionFactory;

    // Save or update a Recruitment
    @Transactional
    public void save(Recruitment recruitment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(recruitment);
    }
    
    // Update a Recruitment Form
    public void update(Recruitment recruitment) {
        try (Session session = sessionFactory.openSession()) {
        	session.beginTransaction();
        	session.createNativeQuery("UPDATE recruitments SET student_class = :student_class, phone_number = :phone_number, parent_phone_number = :parent_phone_number, reason_for_apply = :reason_for_apply WHERE id = :id")
    		.setParameter("student_class", recruitment.getStudentClass())
    		.setParameter("phone_number", recruitment.getPhoneNumber())
    		.setParameter("parent_phone_number", recruitment.getParentPhoneNumber())
    		.setParameter("reason_for_apply", recruitment.getReasonForApply())
    		.setParameter("id", recruitment.getId())
    		.executeUpdate();
        	session.getTransaction().commit();
        }
    }

    // Find a Recruitment by ID
    @Transactional
    public Recruitment findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Recruitment.class, id);
    }
    
    // Find a Recruitment by Email
    @Transactional
    public Recruitment findByUser(User userToFind) {
    	try (Session session = sessionFactory.openSession()) {
            List<Recruitment> rec = session.createQuery("from Recruitment", Recruitment.class).list();
            for (Recruitment recToFind : rec) {
           	 if (recToFind.getUser().getEmail().equals(userToFind.getEmail())) return recToFind;
            }
            return null;
       }
    }

    // Get all Recruitments
    @SuppressWarnings("unchecked")
	@Transactional
    public List<Recruitment> findAll() {
        //Session session = sessionFactory.getCurrentSession();
        //return session.createQuery("from Recruitment", Recruitment.class).list();
        try (Session session = sessionFactory.openSession()) {
        	return session.createQuery("from Recruitment").getResultList();
        }
    }

    // Delete a Recruitment by ID
    @Transactional
    public void deleteById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Recruitment recruitment = session.get(Recruitment.class, id);
        if (recruitment != null) {
            session.delete(recruitment);
        }
    }
}
