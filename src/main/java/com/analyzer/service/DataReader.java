package com.analyzer.service;

import com.analyzer.domain.Log;
import com.analyzer.domain.LogfileEntry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataReader implements DataAcquisition {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataReader.class);
    private final LogAnalyzer logAnalyzer;

    public DataReader(LogAnalyzer logAnalyzer) {
        this.logAnalyzer = logAnalyzer;
    }

    @Override
    public void parseLogfile(String dirPath, Session session) { //TODO should be void method?
        ObjectMapper mapper = new ObjectMapper();
        Transaction transaction = session.beginTransaction();

        try {
            FileReader logfile = new FileReader(dirPath);
            BufferedReader bufferedReader = new BufferedReader(logfile);

            String line;
            int i = 0;

            while ((line = bufferedReader.readLine()) != null) {
                LogfileEntry entry = mapper.readValue(line, LogfileEntry.class);
                Log log = logAnalyzer.convertLogfileEntry(entry);
                if (log != null) {
                    session.save(log);
                    i++;
                }
                if (i > 0 && i % 10 == 0)
                    transaction.commit();
            }
            transaction.commit();
        } catch (IOException e) {
            if (transaction != null)
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
