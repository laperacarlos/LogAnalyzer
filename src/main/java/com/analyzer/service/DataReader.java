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

public class DataReader implements DataAcquisition {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataReader.class);
    private final LogAnalyzer logAnalyzer;

    public DataReader(LogAnalyzer logAnalyzer) {
        this.logAnalyzer = logAnalyzer;
    }

    @Override
    public void parseLogfile(String dirPath, Session session) {
        ObjectMapper mapper = new ObjectMapper();
        Transaction transaction = session.beginTransaction();

        try {
            FileReader logfile = new FileReader(dirPath);
            BufferedReader bufferedReader = new BufferedReader(logfile);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                LogfileEntry entry = mapper.readValue(line, LogfileEntry.class);
                validateEntry(entry);
                Log log = logAnalyzer.convertLogfileEntry(entry);
                if (log != null) {
                    session.save(log);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void validateEntry(LogfileEntry logfileEntry) {
        if (!logfileEntry.isValid()) {
            throw new IllegalStateException("One of log entry values is null: \"id\", \"state\", \"timestamp\"");
        }
    }
}
