//SchoolProgramInfoDAO.java
package com.example.repository;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.example.entity.SchoolProgramInfo;

//sample data
//"SEKOLAH KEBANGSAAN (FELDA) BUKIT WAHA", "JBA3002", false, false, true, true, false, false, false, "https://www.youtube.com";
//"SEKOLAH KEBANGSAAN BUKIT LINTANG", "JBA3003", false, false, true, true, false, false, false, "https://www.youtube.com";
//"SEKOLAH KEBANGSAAN SEDILI BESAR", "JBA3004", false, false, true, true, false, false, false, "https://www.youtube.com";
//"SEKOLAH KEBANGSAAN MAWAI", "JBA3005", true, true, true, true, false, false, false, "https://www.youtube.com";
//"SEKOLAH KEBANGSAAN JOHOR KAMPONG", "JBA3006", false, false, true, true, false, false, false, "https://www.youtube.com";


@Repository
public class SchoolProgramInfoDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public SchoolProgramInfoDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // 1 - Get all records
    public List<SchoolProgramInfo> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from SchoolProgramInfo", SchoolProgramInfo.class).list();
        }
    }

    // 2.1 - Get by schoolCode
    public SchoolProgramInfo findBySchoolCode(String schoolCode) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM SchoolProgramInfo WHERE schoolCode = :schoolCode", SchoolProgramInfo.class)
                          .setParameter("schoolCode", schoolCode)
                          .uniqueResult();
        }
    }
    
    // 2.2 - Get by ID
    public SchoolProgramInfo findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(SchoolProgramInfo.class, id);
        }
    }

    // 3 - Create
    public void save(SchoolProgramInfo schoolProgramInfo) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(schoolProgramInfo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // 4 - Update
    public void update(SchoolProgramInfo schoolProgramInfo) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(schoolProgramInfo);
            transaction.commit();
            System.out.println("Update successful for school: " + schoolProgramInfo.getSchoolCode());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // 5 - Delete
    public void delete(String schoolCode) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            SchoolProgramInfo schoolProgramInfo = findBySchoolCode(schoolCode);
            if (schoolProgramInfo != null) {
                session.delete(schoolProgramInfo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
} 
