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
    @NonNull
    private Long duration;

    @Column
    @NonNull
    private boolean alert;

    @Column
    private String type;

    @Column
    private String host;

    public Log(@NonNull String eventId, @NonNull Long duration, @NonNull boolean alert, String type, String host) {
        this.eventId = eventId;
        this.duration = duration;
        this.alert = alert;
        this.type = type;
        this.host = host;
    }
}
