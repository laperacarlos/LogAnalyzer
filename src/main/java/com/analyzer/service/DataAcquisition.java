package com.analyzer.service;

import com.analyzer.domain.Log;
import org.hibernate.Session;

import java.util.List;

//TODO some annotation here??? @Component?

public interface DataAcquisition {
    List<Log> parseLogfile(String dirPath, Session session);
}
