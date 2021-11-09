package com.analyzer.service;

import com.analyzer.domain.Log;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataReaderTestSuite {

    private DataReader dataReader;
    private static Session session;
    private static SessionFactory sessionFactory;

    @BeforeAll
    static void createHibernateSession() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.getCurrentSession();
    }

    @BeforeEach
    void createReader() {
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        dataReader =  new DataReader(logAnalyzer);
    }

    @Test
    void shouldParseLogfile() {
        //when
        List<Log> parsedList = dataReader.parseLogfile("C:\\Development\\Projects\\log-analyzer\\src\\test\\resources\\logfile.txt", session);

        //then
        assertEquals(2, parsedList.size());
        assertEquals("scsmbstgra", parsedList.get(0).getId());
        assertEquals("APPLICATION_LOG", parsedList.get(0).getType());
        //assertEquals(State.STARTED, parsedList.get(0).getState());
        //assertEquals(1491377495212L, parsedList.get(0).getTimestamp());
        assertEquals("12345", parsedList.get(0).getHost());
        assertNull(parsedList.get(1).getHost());
        assertNull(parsedList.get(1).getType());
    }
}
