package com.analyzer.service;

import com.analyzer.domain.Log;
import com.analyzer.utility.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataReaderTestSuite {
    private static final String testLogfile1 = "C:\\Development\\Projects\\log-analyzer\\src\\test\\resources\\testLogfile1.txt";

    private DataReader dataReader;
    private Session session;

    @BeforeEach
    void createHibernateSession() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @BeforeEach
    void createReader() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        dataReader = new DataReader(logAnalyzer);
    }

    @AfterEach
    void cleanDatabase() {
        Transaction transaction = session.beginTransaction();
        session.createQuery("delete from logs").executeUpdate();
        transaction.commit();
    }

    @Test
    void shouldParseLogfile() {

        //when
        dataReader.parseLogfile(testLogfile1, session);
        List<Log> logList = session.createQuery("from logs", Log.class).list();

        //then
        assertEquals(3, logList.size());
        assertEquals("scsmbstgra", logList.get(0).getEventId());
        assertEquals("APPLICATION_LOG", logList.get(0).getType());
        assertEquals(5, logList.get(0).getDuration());
        assertTrue(logList.get(0).isAlert());
        assertEquals("12345", logList.get(0).getHost());
    }
}
