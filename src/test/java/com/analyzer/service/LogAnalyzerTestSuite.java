package com.analyzer.service;


import com.analyzer.domain.Log;
import com.analyzer.domain.LogfileEntry;
import com.analyzer.domain.State;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogAnalyzerTestSuite {
    private static LogAnalyzer logAnalyzer;

    @BeforeAll
    static void createGivenAnalyzer() {
        logAnalyzer = new LogAnalyzer();
    }

    @Test
    void shouldConvertToLogs() {
        //given
        LogfileEntry logfileEntry1 = new LogfileEntry("id1", State.STARTED, 12345467L, "APP_ŹŹ", "777");
        LogfileEntry logfileEntry2 = new LogfileEntry("id1", State.FINISHED, 12345469L, "APP_ŹŹ", "777");
        LogfileEntry logfileEntry3 = new LogfileEntry("id2", State.STARTED, 12345460L, "APP_ĘĘ", "666");
        LogfileEntry logfileEntry4 = new LogfileEntry("id2", State.FINISHED, 12345465L, "APP_ĘĘ", "666");

        //when
        logAnalyzer.convertLogfileEntry(logfileEntry1);
        logAnalyzer.convertLogfileEntry(logfileEntry3);
        Log log1 = logAnalyzer.convertLogfileEntry(logfileEntry2);
        Log log2 = logAnalyzer.convertLogfileEntry(logfileEntry4);

        //then
        assertEquals("id1", log1.getEventId());
        assertEquals("APP_ŹŹ", log1.getType());
        assertEquals("777", log1.getHost());
        assertFalse(log1.isAlert());
        assertTrue(log2.isAlert());
    }

    @Test
    void shouldReturnNull() {
        //given
        LogfileEntry logfileEntry1 = new LogfileEntry("id1", State.STARTED, 12345467L, "APP_ŹŹ", "777");
        LogfileEntry logfileEntry2 = new LogfileEntry("id2", State.STARTED, 12345460L, "APP_ĘĘ", "666");

        //when
        Log log1 = logAnalyzer.convertLogfileEntry(logfileEntry1);
        Log log2 = logAnalyzer.convertLogfileEntry(logfileEntry2);

        //then
        assertNull(log1);
        assertNull(log2);
    }
}
