package com.analyzer.domain;

import lombok.Getter;

@Getter
public class LogfileEntry {
    private String id;
    private State state;
    private Long timestamp;
    private String type;
    private String host;
}
