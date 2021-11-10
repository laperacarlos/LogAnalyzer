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

public class DataReaderTest {
    private static final String testLogfile1 = "src/test/resources/testLogfile1.txt";
    private static final String testLogfile2 = "src/test/resources/testLogfile2.txt";
    private static final String testLogfile3 = "src/test/resources/testLogfile3.txt";
    private static final String testLogfile4 = "src/test/resources/testLogfile4.txt";
    private static final String testLogfile5 = "src/test/resources/testLogfile5.txt";
    private static final String testLogfile6 = "src/test/resources/testLogfile6.txt";

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
        session.close();
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

    @Test
    void shouldReturnNull() {

    }

    @Test
    void shouldRollbackTransactionWithWrongTimestamp() {
        //when
        dataReader.parseLogfile(testLogfile2, session);
        List<Log> logList = session.createQuery("from logs", Log.class).list();

        //then
        assertEquals(0, logList.size());
    }

    @Test
    void shouldRollbackTransactionWithWrongState() {
        //when
        dataReader.parseLogfile(testLogfile3, session);
        List<Log> logList = session.createQuery("from logs", Log.class).list();

        //then
        assertEquals(0, logList.size());
    }

    @Test
    void shouldRollbackTransactionWithoutTimestamp() {
        //when
        dataReader.parseLogfile(testLogfile4, session);
        List<Log> logList = session.createQuery("from logs", Log.class).list();

        //then
        assertEquals(0, logList.size());
    }

    @Test
    void shouldRollbackTransactionWithoutId() {
        //when
        dataReader.parseLogfile(testLogfile5, session);
        List<Log> logList = session.createQuery("from logs", Log.class).list();

        //then
        assertEquals(0, logList.size());
    }

    @Test
    void shouldRollbackTransactionWithoutState() {
        //when
        dataReader.parseLogfile(testLogfile6, session);
        List<Log> logList = session.createQuery("from logs", Log.class).list();

        //then
        assertEquals(0, logList.size());
    }
}
