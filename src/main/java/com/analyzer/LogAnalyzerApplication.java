package com.analyzer;

import com.analyzer.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LogAnalyzerApplication {

    public static void main(String[] args) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();
            // save the student objects
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

}
