package com.kavita.ops_agent;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
public class Incident {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String title;
    private String severity;   // LOW, MEDIUM, HIGH
    private String status;     // OPEN, RESOLVED
    private String service;
    private LocalDateTime createdAt = LocalDateTime.now();
}