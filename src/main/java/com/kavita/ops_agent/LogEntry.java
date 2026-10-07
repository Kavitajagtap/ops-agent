package com.kavita.ops_agent;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class LogEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String service;
    private String level;      // INFO, WARN, ERROR
    @Column(length = 1000) private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
}