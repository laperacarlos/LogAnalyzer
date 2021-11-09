package com.analyzer.service;

import org.hibernate.Session;

public interface DataAcquisition {
    void parseLogfile(String dirPath, Session session);
}
