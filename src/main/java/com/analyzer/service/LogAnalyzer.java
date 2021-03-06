package com.analyzer.service;

import com.analyzer.domain.Log;
import com.analyzer.domain.LogfileEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class LogAnalyzer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAnalyzer.class);
    private final Map<String, Long> logMap = new HashMap<>();

    public Log convertLogfileEntry(final LogfileEntry logfileEntry) {
        String eventId = logfileEntry.getId();
        if (logMap.containsKey(eventId)) {
            LOGGER.debug("Completed event: " + eventId);
            return createLog(logfileEntry);
        } else {
            logMap.put(eventId, logfileEntry.getTimestamp());
            LOGGER.debug("Event with one log: " + eventId);
            return null;
        }
    }

    private Log createLog(LogfileEntry logfileEntry) {
        String eventId = logfileEntry.getId();
        Long duration = calcDuration(eventId, logfileEntry.getTimestamp());
        boolean alert = validateTime(duration);
        return new Log(eventId, duration, alert, logfileEntry.getType(), logfileEntry.getHost());
    }

    private Long calcDuration(String eventId, Long firstTimestamp) {
        Long secondTimestamp = logMap.get(eventId);
        return Math.abs(firstTimestamp - secondTimestamp);
    }

    private boolean validateTime(Long duration) {
        return duration > 4;
    }
}
