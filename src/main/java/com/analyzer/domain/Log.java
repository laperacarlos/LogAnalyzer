package com.analyzer.domain;

import lombok.*;

import javax.persistence.*;

@Entity(name = "logs")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NonNull
    private String eventId;

    @Column
    private Long duration;
    //TODO if event is only started log info that event is not finished

    @Column
    private boolean alert;

    @Column
    private String type;

    @Column
    private String host;

    public Log(@NonNull String eventId, Long duration, boolean alert, String type, String host) {
        this.eventId = eventId;
        this.duration = duration;
        this.alert = alert;
        this.type = type;
        this.host = host;
    }
}
