package com.analyzer.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogfileEntry {
    private String id;
    private State state;
    private Long timestamp;
    private String type;
    private String host;

    public boolean isValid() {
        return id != null && state != null && timestamp != null;
    }
}
