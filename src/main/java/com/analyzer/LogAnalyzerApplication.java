package com.analyzer;

import com.analyzer.service.DataAcquisition;
import com.analyzer.service.DataReader;
import com.analyzer.service.LogAnalyzer;
import com.analyzer.utility.HibernateUtil;
import org.hibernate.Session;

public class LogAnalyzerApplication {

    public static void main(String[] args) {

        LogAnalyzer logAnalyzer = new LogAnalyzer();
        DataAcquisition reader = new DataReader(logAnalyzer);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //  reader.parseLogfile(args[0], session);
        } catch (Exception e) {
            //todo logger here?
            e.printStackTrace();
        }
    }
}
