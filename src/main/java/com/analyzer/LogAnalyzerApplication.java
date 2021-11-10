package com.analyzer;

import com.analyzer.service.DataAcquisition;
import com.analyzer.service.DataReader;
import com.analyzer.service.LogAnalyzer;
import com.analyzer.utility.HibernateUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogAnalyzerApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzerApplication.class);

    public static void main(String[] args) {

        if (args.length != 0) {
            LogAnalyzer logAnalyzer = new LogAnalyzer();
            DataAcquisition reader = new DataReader(logAnalyzer);
            String filePath = args[0];

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                reader.parseLogfile(filePath, session);
                session.close();
                LOGGER.info("File has ben parsed successfully!");
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            LOGGER.info("File path argument is missing. Pass it while running application.");
        }
    }
}
